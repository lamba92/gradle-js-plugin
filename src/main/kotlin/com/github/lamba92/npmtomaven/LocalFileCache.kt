package com.github.lamba92.npmtomaven

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.copyTo
import kotlin.io.path.createDirectories
import kotlin.io.path.writeBytes

class LocalFileCache(
    val path: Path = Paths.get(System.getenv("NPM_FILE_CACHE") ?: "./npm-cache")
) : NormalizedFileCache() {

    init {
        path.createDirectories()
    }

    private val syncMutex = Mutex()
    private val mutexMap = mutableMapOf<String, Mutex>()

    override suspend fun getFileUsingNormalizedKey(normalizedKey: String): File? {
        val mutex = syncMutex.withLock { mutexMap.getOrPut(normalizedKey) { Mutex() }.also { it.lock() } }
        val file = path.resolve(normalizedKey).toFile().takeIf { it.exists() }
        mutex.unlock()
        return file
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun saveFileUsingNormalizedKey(file: File, normalizedKey: String) {
        val mutex = syncMutex.withLock { mutexMap.getOrPut(normalizedKey) { Mutex() }.also { it.lock() } }
        withContext(Dispatchers.IO) {
            path.createDirectories()
            file.toPath().copyTo(path.resolve(normalizedKey), true)
        }
        mutex.unlock()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun saveFileUsingNormalizedKey(bytes: ByteArray, normalizedKey: String): File {
        val mutex = syncMutex.withLock { mutexMap.getOrPut(normalizedKey) { Mutex() }.also { it.lock() } }
        withContext(Dispatchers.IO) {
            path.createDirectories()
            path.resolve(normalizedKey).writeBytes(bytes)
        }
        mutex.unlock()
        return path.resolve(normalizedKey).toFile()
    }
}