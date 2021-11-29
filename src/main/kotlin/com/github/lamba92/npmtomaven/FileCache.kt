package com.github.lamba92.npmtomaven

import java.io.File

interface FileCache {
    suspend fun getFile(key: String): File?
    suspend fun saveFile(file: File, key: String)
    suspend fun saveFile(bytes: ByteArray, key: String)
}