package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.NpmVersionedPackageMetadata
import com.github.yuchi.semver.Version
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.serialization.XML
import org.slf4j.Logger
import java.io.File

suspend fun PipelineContext<Any, ApplicationCall>.respondGradleMetadataSha1(
    objectCache: ObjectCache,
    key: String,
    security: Security,
    json: Json,
    packageName: String,
    packageVersion: Version,
    tarballKey: String,
    tarballFile: suspend () -> File,
    client: HttpClient,
    npmMetadata: NpmVersionedPackageMetadata,
    publicationDate: Instant,
    tarballSha512: suspend () -> String,
    tarballSha256: suspend () -> String,
    tarballSha1: suspend () -> String,
    tarballMd5: suspend () -> String
) {
    call.respondText {
        objectCache.getOrPutObject("$key.module.sha1") {
            security.sha1.digestAsHex(
                getOrCacheModule(
                    objectCache = objectCache,
                    key = key,
                    json = json,
                    packageName = packageName,
                    packageVersion = packageVersion,
                    tarballKey = tarballKey,
                    tarballFile = tarballFile,
                    client = client,
                    npmMetadata = npmMetadata,
                    publicationDate = publicationDate,
                    tarballSha512 = tarballSha512,
                    tarballSha256 = tarballSha256,
                    tarballSha1 = tarballSha1,
                    tarballMd5 = tarballMd5,
                )
            )
        }
    }
}

suspend fun ApplicationCall.respondPomSha1(
    objectCache: ObjectCache,
    key: String,
    security: Security,
    xml: XML,
    packageName: String,
    packageVersion: Version,
    npmMetadata: NpmVersionedPackageMetadata,
    publicationDate: Instant,
    client: HttpClient
) = respondText {
    objectCache.getOrPutObject("$key.pom.sha1") {
        security.sha1.digestAsHex(
            getOrCachePom(
                objectCache = objectCache,
                key = key,
                xml = xml,
                packageName = packageName,
                packageVersion = packageVersion,
                npmVersionedPackageMetadata = npmMetadata,
                publicationDate = publicationDate,
                client = client,
                logger = application.log
            )
        )
    }
}

suspend fun getOrCacheModule(
    objectCache: ObjectCache,
    key: String,
    json: Json,
    packageName: String,
    packageVersion: Version,
    tarballKey: String,
    tarballFile: suspend () -> File,
    client: HttpClient,
    npmMetadata: NpmVersionedPackageMetadata,
    publicationDate: Instant,
    tarballSha512: suspend () -> String,
    tarballSha256: suspend () -> String,
    tarballSha1: suspend () -> String,
    tarballMd5: suspend () -> String
) = objectCache.getOrPutObject("$key.module") {
    generateGradleMetadata(
        json = json,
        packageName = packageName,
        packageVersion = packageVersion.toString(),
        fileKey = tarballKey,
        file = tarballFile,
        client = client,
        objectCache = objectCache,
        npmMetadata = npmMetadata,
        publicationDate = publicationDate,
        sha512 = tarballSha512,
        sha256 = tarballSha256,
        sha1 = tarballSha1,
        md5 = tarballMd5
    )
}

suspend fun getOrCachePom(
    objectCache: ObjectCache,
    key: String,
    xml: XML,
    packageName: String,
    packageVersion: Version,
    npmVersionedPackageMetadata: NpmVersionedPackageMetadata,
    publicationDate: Instant,
    client: HttpClient,
    logger: Logger
) = objectCache.getOrPutObject("$key.pom") {
    generatePom(
        xml = xml,
        packageName = packageName,
        packageVersion = packageVersion.toString(),
        npmVersionedPackageMetadata = npmVersionedPackageMetadata,
        publicationDate = publicationDate,
        client = client,
        logger = logger
    )
}