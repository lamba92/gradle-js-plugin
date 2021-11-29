package com.github.lamba92.npmtomaven

import java.io.File

abstract class NormalizedFileCache : FileCache {

    protected abstract suspend fun getFileUsingNormalizedKey(normalizedKey: String): File?

    protected abstract suspend fun saveFileUsingNormalizedKey(file: File, normalizedKey: String)

    protected abstract suspend fun saveFileUsingNormalizedKey(bytes: ByteArray, normalizedKey: String): File

    private fun normalizeKey(key: String) =
        key.map { if (it.isDigit() || it.isLetter() || it == '.') it else '_' }.joinToString("")

    override suspend fun getFile(key: String) =
        getFileUsingNormalizedKey(normalizeKey(key))

    override suspend fun saveFile(file: File, key: String) =
        saveFileUsingNormalizedKey(file, normalizeKey(key))

    override suspend fun saveFile(bytes: ByteArray, key: String) =
        saveFileUsingNormalizedKey(bytes, normalizeKey(key))
}