@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")


package externals.aws

import kotlin.js.Json

external interface APIGatewayAuthorizerResultContext {
    @nativeGetter
    operator fun get(name: String): dynamic /* String? | Number? | Boolean? */
    @nativeSetter
    operator fun set(name: String, value: String?)
    @nativeSetter
    operator fun set(name: String, value: Number?)
    @nativeSetter
    operator fun set(name: String, value: Boolean?)
}

typealias APIGatewayEventRequestContext = APIGatewayEventRequestContextWithAuthorizer<Json?>


external interface APIGatewayEventRequestContextWithAuthorizer<TAuthorizerContext> {
    var accountId: String
    var apiId: String
    var authorizer: TAuthorizerContext
    var connectedAt: Number?
        get() = definedExternally
        set(value) = definedExternally
    var connectionId: String?
        get() = definedExternally
        set(value) = definedExternally
    var domainName: String?
        get() = definedExternally
        set(value) = definedExternally
    var domainPrefix: String?
        get() = definedExternally
        set(value) = definedExternally
    var eventType: String?
        get() = definedExternally
        set(value) = definedExternally
    var extendedRequestId: String?
        get() = definedExternally
        set(value) = definedExternally
    var protocol: String
    var httpMethod: String
    var identity: APIGatewayEventIdentity
    var messageDirection: String?
        get() = definedExternally
        set(value) = definedExternally
    var messageId: String?
        get() = definedExternally
        set(value) = definedExternally
    var path: String
    var stage: String
    var requestId: String
    var requestTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestTimeEpoch: Number
    var resourceId: String
    var resourcePath: String
    var routeKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$0` {
    var notAfter: String
    var notBefore: String
}

external interface APIGatewayEventClientCertificate {
    var clientCertPem: String
    var serialNumber: String
    var subjectDN: String
    var issuerDN: String
    var validity: `T$0`
}

external interface APIGatewayEventIdentity {
    var accessKey: String?
    var accountId: String?
    var apiKey: String?
    var apiKeyId: String?
    var caller: String?
    var clientCert: APIGatewayEventClientCertificate?
    var cognitoAuthenticationProvider: String?
    var cognitoAuthenticationType: String?
    var cognitoIdentityId: String?
    var cognitoIdentityPoolId: String?
    var principalOrgId: String?
    var sourceIp: String
    var user: String?
    var userAgent: String?
    var userArn: String?
}