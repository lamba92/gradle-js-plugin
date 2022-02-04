package com.github.lamba92.npmtomaven.sql

import org.jetbrains.exposed.sql.Table

object NpmPackageHashesTable : Table("NpmPackageHashes") {
    val packageName = varchar("packageName", 255)
    val packageVersion = varchar("packageVersion", 255)
    val tarballSha512 = varchar("tarballSha512", 255)
    val tarballSha256 = varchar("tarballSha256", 255)
    val tarballSha1 = varchar("tarballSha1", 255)
    val tarballMd5 = varchar("tarballMd5", 255)
    val pomSha1 = varchar("pomSha1", 255)
    val moduleSha1 = varchar("moduleSha1", 255)
    val tarballSize = long("tarballSize")

    override val primaryKey = PrimaryKey(packageName, packageVersion)
}