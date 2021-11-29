package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.NpmMetadata
import com.github.yuchi.semver.Version
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.Deferred
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
    tarballFile: Deferred<File>,
    client: HttpClient,
    npmMetadata: NpmMetadata,
    tarballSha512: Deferred<String>,
    tarballSha256: Deferred<String>,
    tarballSha1: Deferred<String>,
    tarballMd5: Deferred<String>
) {
    call.respondText {
        objectCache.getOrPutObject("$key.module.sha1") {
            security.sha1.digestAsHex(
                getOrCacheModule(
                    objectCache,
                    key,
                    json,
                    packageName,
                    packageVersion,
                    tarballKey,
                    tarballFile,
                    client,
                    npmMetadata,
                    tarballSha512,
                    tarballSha256,
                    tarballSha1,
                    tarballMd5
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
    npmMetadata: NpmMetadata,
    generateGradleMetadata: Boolean,
    client: HttpClient
) = respondText {
    objectCache.getOrPutObject("$key.pom.sha1") {
        security.sha1.digestAsHex(
            getOrCachePom(
                objectCache,
                key,
                xml,
                packageName,
                packageVersion,
                npmMetadata,
                generateGradleMetadata,
                client,
                application.log
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
    tarballFile: Deferred<File>,
    client: HttpClient,
    npmMetadata: NpmMetadata,
    tarballSha512: Deferred<String>,
    tarballSha256: Deferred<String>,
    tarballSha1: Deferred<String>,
    tarballMd5: Deferred<String>
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
    npmMetadata: NpmMetadata,
    generateGradleMetadata: Boolean,
    client: HttpClient,
    logger: Logger
) = objectCache.getOrPutObject("$key.pom") {
    generatePom(
        xml = xml,
        packageName = packageName,
        packageVersion = packageVersion.toString(),
        npmMetadata = npmMetadata,
        addMetadataAnnotation = generateGradleMetadata,
        client = client,
        logger = logger
    )
}