package com.github.lamba92.npmtomaven

import java.io.File

interface FileCache {
    suspend fun getFile(key: String): File?
    suspend fun saveFile(file: File, key: String)
    suspend fun saveFile(bytes: ByteArray, key: String): File
}

suspend fun FileCache.getOrPutBytes(key: String, function: suspend () -> ByteArray) =
    getFile(key) ?: saveFile(function(), key)

suspend fun FileCache.getOrPutFile(key: String, function: suspend () -> File) =
    getFile(key) ?: function().also { saveFile(it, key) }