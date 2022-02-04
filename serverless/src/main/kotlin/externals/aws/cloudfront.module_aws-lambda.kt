@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$1` {
    var key: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: String
}

external interface CloudFrontHeaders {
    @nativeGetter
    operator fun get(name: String): Array<`T$1`>?
    @nativeSetter
    operator fun set(name: String, value: Array<`T$1`>)
}

external interface CloudFrontCustomOrigin {
    var customHeaders: CloudFrontHeaders
    var domainName: String
    var keepaliveTimeout: Number
    var path: String
    var port: Number
    var protocol: String /* "http" | "https" */
    var readTimeout: Number
    var sslProtocols: Array<String>
}

external interface CloudFrontS3Origin {
    var authMethod: String /* "origin-access-identity" | "none" */
    var customHeaders: CloudFrontHeaders
    var domainName: String
    var path: String
    var region: String
}

external interface CloudFrontResponse {
    var status: String
    var statusDescription: String
    var headers: CloudFrontHeaders
}

external interface `T$2` {
    var action: String /* "read-only" | "replace" */
    var data: String
    var encoding: String /* "base64" | "text" */
    var inputTruncated: Boolean
}

external interface CloudFrontRequest {
    var body: `T$2`?
        get() = definedExternally
        set(value) = definedExternally
    var clientIp: String
    var method: String
    var uri: String
    var querystring: String
    var headers: CloudFrontHeaders
    var origin: dynamic /* `T$12`? | `T$13`? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$3` {
    var distributionDomainName: String
    var distributionId: String
    var eventType: String /* "origin-request" | "origin-response" | "viewer-request" | "viewer-response" */
    var requestId: String
}

external interface CloudFrontEvent {
    var config: `T$3`
}

external interface CloudFrontResultResponse {
    var status: String
    var statusDescription: String?
        get() = definedExternally
        set(value) = definedExternally
    var headers: CloudFrontHeaders?
        get() = definedExternally
        set(value) = definedExternally
    var bodyEncoding: String? /* "text" | "base64" */
        get() = definedExternally
        set(value) = definedExternally
    var body: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$4` {
    var value: String
    var attributes: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$5` {
    var value: String
    var attributes: String?
        get() = definedExternally
        set(value) = definedExternally
    var multiValue: Array<`T$4`>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CloudFrontFunctionsCookies {
    @nativeGetter
    operator fun get(key: String): `T$5`?
    @nativeSetter
    operator fun set(key: String, value: `T$5`)
}

external interface `T$6` {
    var value: String
}

external interface `T$7` {
    var value: String
    var multiValue: Array<`T$6`>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CloudFrontFunctionsQuerystring {
    @nativeGetter
    operator fun get(key: String): `T$7`?
    @nativeSetter
    operator fun set(key: String, value: `T$7`)
}

external interface CloudFrontFunctionsHeaders {
    @nativeGetter
    operator fun get(key: String): `T$7`?
    @nativeSetter
    operator fun set(key: String, value: `T$7`)
}

external interface `T$8` {
    var distributionDomainName: String
    var distributionId: String
    var eventType: String /* "viewer-request" | "viewer-response" */
    var requestId: String
}

external interface `T$9` {
    var ip: String
}

external interface `T$10` {
    var method: String
    var uri: String
    var querystring: CloudFrontFunctionsQuerystring
    var headers: CloudFrontFunctionsHeaders
    var cookies: CloudFrontFunctionsCookies
}

external interface `T$11` {
    var statusCode: Number
    var statusDescription: String?
        get() = definedExternally
        set(value) = definedExternally
    var headers: CloudFrontFunctionsHeaders
    var cookies: CloudFrontFunctionsCookies
}

external interface CloudFrontFunctionsEvent {
    var version: String
    var context: `T$8`
    var viewer: `T$9`
    var request: `T$10`
    var response: `T$11`
}