package externals.aws

import kotlin.js.Json

typealias APIGatewayEvent = APIGatewayProxyEvent

typealias APIGatewayProxyEvent = APIGatewayProxyEventBase<Json?>

external interface APIGatewayProxyEventBase<TAuthorizerContext> {
    var body: String?
    var headers: APIGatewayProxyEventHeaders?
    var multiValueHeaders: APIGatewayProxyEventMultiValueHeaders?
    var httpMethod: String?
    var isBase64Encoded: Boolean?
    var path: String
    var pathParameters: APIGatewayProxyEventPathParameters?
    var queryStringParameters: APIGatewayProxyEventQueryStringParameters?
    var multiValueQueryStringParameters: APIGatewayProxyEventMultiValueQueryStringParameters?
    var stageVariables: APIGatewayProxyEventStageVariables?
    var requestContext: APIGatewayEventRequestContextWithAuthorizer<TAuthorizerContext>?
    var resource: String?
}

external interface APIGatewayProxyEventHeaders {
    operator fun get(name: String): String?
    operator fun set(name: String, value: String?)
}

external interface APIGatewayProxyEventMultiValueHeaders {
    operator fun get(name: String): Array<String>?
    operator fun set(name: String, value: Array<String>?)
}

external interface APIGatewayProxyEventPathParameters {
    operator fun get(name: String): String?
    operator fun set(name: String, value: String?)
}

external interface APIGatewayProxyEventQueryStringParameters {
    operator fun get(name: String): String?
    operator fun set(name: String, value: String?)
}

external interface APIGatewayProxyEventMultiValueQueryStringParameters {
    operator fun get(name: String): Array<String>?
    operator fun set(name: String, value: Array<String>?)
}

external interface APIGatewayProxyEventStageVariables {
    operator fun get(name: String): String?
    operator fun set(name: String, value: String?)
}

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

external interface APIGatewayEventClientCertificate {
    var clientCertPem: String
    var serialNumber: String
    var subjectDN: String
    var issuerDN: String
    var validity: Validity
}

external interface Validity {
    var notAfter: String
    var notBefore: String
}
