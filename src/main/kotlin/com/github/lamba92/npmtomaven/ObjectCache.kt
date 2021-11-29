package com.github.lamba92.npmtomaven

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.kodein.db.DB
import org.kodein.db.impl.open
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.*
import kotlin.reflect.KClass
import kotlin.reflect.full.createType

interface ObjectCache {
    suspend fun <T : Any> getObject(key: String, type: KClass<T>): T?
    suspend fun <T : Any> saveObject(`object`: T, key: String)
}

suspend inline fun <reified T : Any> ObjectCache.getOrPutObject(key: String, crossinline action: suspend () -> T): T =
    getObject(key) ?: action().also { saveObject(it, key) }

suspend inline fun <reified T : Any> ObjectCache.getObject(key: String) =
    getObject(key, T::class)

abstract class NormalizedObjectCache : ObjectCache {

    protected abstract suspend fun <T : Any> getObjectUsingNormalizedKey(normalizedKey: String, type: KClass<T>): T?

    protected abstract suspend fun <T : Any> saveObjectUsingNormalizedKey(`object`: T, normalizedKey: String)

    private fun normalizeKey(key: String) =
        key.map { if (it.isDigit() || it.isLetter() || it == '.') it else '_' }.joinToString("")

    override suspend fun <T : Any> getObject(key: String, type: KClass<T>): T? =
        getObjectUsingNormalizedKey(normalizeKey(key), type)

    override suspend fun <T : Any> saveObject(`object`: T, key: String) =
        saveObjectUsingNormalizedKey(`object`, normalizeKey(key))

}

class KodeinDBObjectCache(
    path: Path = Paths.get(System.getenv("NPM_OBJECT_CACHE") ?: "./npm-object-cache"),
    val json: Json
) :
    NormalizedObjectCache() {

    private val db = DB.open(path.absolutePathString())

    override suspend fun <T : Any> getObjectUsingNormalizedKey(normalizedKey: String, type: KClass<T>) =
        db[String::class, db.keyById(String::class, normalizedKey)]
            ?.let {
                @Suppress("UNCHECKED_CAST")
                json.decodeFromString(serializer(type.createType()) as DeserializationStrategy<T>, it)
            }

    override suspend fun <T : Any> saveObjectUsingNormalizedKey(`object`: T, normalizedKey: String) {
        db.put(db.keyById(String::class), json.encodeToString(serializer(`object`::class.createType()), `object`))
    }
}

class FileBasedObjectCache(
    val path: Path = Paths.get(System.getenv("NPM_OBJECT_CACHE") ?: "./npm-object-cache"),
    val json: Json
) : NormalizedObjectCache() {

    private val syncMutex = Mutex()
    private val mutexMap = mutableMapOf<String, Mutex>()

    override suspend fun <T : Any> getObjectUsingNormalizedKey(normalizedKey: String, type: KClass<T>): T? {
        val mutex = syncMutex.withLock { mutexMap.getOrPut(normalizedKey) { Mutex() }.also { it.lock() } }
        @Suppress("BlockingMethodInNonBlockingContext")
        return withContext(Dispatchers.IO) {
            path.createDirectories()
            val file = path.resolve(normalizedKey).takeIf { it.exists() }
            mutex.unlock()
            file?.readText()
        }?.let {
            @Suppress("UNCHECKED_CAST")
            json.decodeFromString(serializer(type.createType()) as DeserializationStrategy<T>, it)
        }
    }

    override suspend fun <T : Any> saveObjectUsingNormalizedKey(`object`: T, normalizedKey: String) {
        val mutex = syncMutex.withLock { mutexMap.getOrPut(normalizedKey) { Mutex() }.also { it.lock() } }
        @Suppress("BlockingMethodInNonBlockingContext")
        withContext(Dispatchers.IO) {
            path.createDirectories()
            path.resolve(normalizedKey)
                .writeText(json.encodeToString(serializer(`object`::class.createType()), `object`))
        }
        mutex.unlock()
    }
}