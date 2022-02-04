@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

import kotlin.js.Json

typealias APIGatewayProxyHandler = Handler<APIGatewayProxyEvent, APIGatewayProxyResult>

typealias APIGatewayProxyCallback = Callback<APIGatewayProxyResult>

typealias APIGatewayProxyHandlerV2<T> = Handler<APIGatewayProxyEventV2, dynamic /* externals.aws.APIGatewayProxyStructuredResultV2 | String | T */>

typealias APIGatewayProxyHandlerV2WithJWTAuthorizer<T> = Handler<APIGatewayProxyEventV2WithJWTAuthorizer, dynamic /* externals.aws.APIGatewayProxyStructuredResultV2 | String | T */>

typealias APIGatewayProxyHandlerV2WithLambdaAuthorizer<TAuthorizerContext, T> = Handler<APIGatewayProxyEventV2WithLambdaAuthorizer<TAuthorizerContext>, dynamic /* externals.aws.APIGatewayProxyStructuredResultV2 | String | T */>

typealias APIGatewayProxyCallbackV2 = Callback<dynamic /* externals.aws.APIGatewayProxyStructuredResultV2 | String | any */>

typealias APIGatewayProxyEvent = APIGatewayProxyEventBase<Json?>

typealias APIGatewayProxyWithLambdaAuthorizerHandler<TAuthorizerContext> = Handler<APIGatewayProxyWithLambdaAuthorizerEvent<TAuthorizerContext>, APIGatewayProxyResult>

typealias APIGatewayProxyWithCognitoAuthorizerHandler = Handler<APIGatewayProxyWithCognitoAuthorizerEvent, APIGatewayProxyResult>

typealias APIGatewayProxyWithLambdaAuthorizerEvent<TAuthorizerContext> = APIGatewayProxyEventBase<Any /* Any & `T$31` */>

typealias APIGatewayProxyWithLambdaAuthorizerEventRequestContext<TAuthorizerContext> = APIGatewayEventRequestContextWithAuthorizer<Any /* Any & `T$31` */>

typealias APIGatewayProxyWithCognitoAuthorizerEvent = APIGatewayProxyEventBase<APIGatewayProxyCognitoAuthorizer>

external interface APIGatewayProxyCognitoAuthorizer {
    var claims: `T$17`
}

external interface APIGatewayProxyEventHeaders {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface APIGatewayProxyEventMultiValueHeaders {
    @nativeGetter
    operator fun get(name: String): Array<String>?
    @nativeSetter
    operator fun set(name: String, value: Array<String>?)
}

external interface APIGatewayProxyEventPathParameters {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface APIGatewayProxyEventQueryStringParameters {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface APIGatewayProxyEventMultiValueQueryStringParameters {
    @nativeGetter
    operator fun get(name: String): Array<String>?
    @nativeSetter
    operator fun set(name: String, value: Array<String>?)
}

external interface APIGatewayProxyEventStageVariables {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

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

external interface APIGatewayProxyResult {
    var statusCode: Number
    var headers: `T$15`?
        get() = definedExternally
        set(value) = definedExternally
    var multiValueHeaders: `T$16`?
        get() = definedExternally
        set(value) = definedExternally
    var body: String
    var isBase64Encoded: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$27` {
    var clientCert: APIGatewayEventClientCertificate
}

external interface `T$28` {
    var method: String
    var path: String
    var protocol: String
    var sourceIp: String
    var userAgent: String
}

external interface APIGatewayEventRequestContextV2 {
    var accountId: String
    var apiId: String
    var authentication: `T$27`?
        get() = definedExternally
        set(value) = definedExternally
    var domainName: String
    var domainPrefix: String
    var http: `T$28`
    var requestId: String
    var routeKey: String
    var stage: String
    var time: String
    var timeEpoch: Number
}

external interface APIGatewayProxyEventV2WithRequestContext<TRequestContext> {
    var version: String
    var routeKey: String
    var rawPath: String
    var rawQueryString: String
    var cookies: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var headers: APIGatewayProxyEventHeaders
    var queryStringParameters: APIGatewayProxyEventQueryStringParameters?
        get() = definedExternally
        set(value) = definedExternally
    var requestContext: TRequestContext
    var body: String?
        get() = definedExternally
        set(value) = definedExternally
    var pathParameters: APIGatewayProxyEventPathParameters?
        get() = definedExternally
        set(value) = definedExternally
    var isBase64Encoded: Boolean
    var stageVariables: APIGatewayProxyEventStageVariables?
        get() = definedExternally
        set(value) = definedExternally
}

external interface APIGatewayEventRequestContextLambdaAuthorizer<TAuthorizerContext> {
    var lambda: TAuthorizerContext
}

external interface `T$29` {
    @nativeGetter
    operator fun get(name: String): dynamic /* String? | Number? | Boolean? | Array<String>? */
    @nativeSetter
    operator fun set(name: String, value: String)
    @nativeSetter
    operator fun set(name: String, value: Number)
    @nativeSetter
    operator fun set(name: String, value: Boolean)
    @nativeSetter
    operator fun set(name: String, value: Array<String>)
}

external interface `T$30` {
    var claims: `T$29`
    var scopes: Array<String>
}

external interface APIGatewayEventRequestContextJWTAuthorizer {
    var principalId: String
    var integrationLatency: Number
    var jwt: `T$30`
}

typealias APIGatewayProxyEventV2WithJWTAuthorizer = APIGatewayProxyEventV2WithRequestContext<APIGatewayEventRequestContextV2WithAuthorizer<APIGatewayEventRequestContextJWTAuthorizer>>

typealias APIGatewayProxyEventV2WithLambdaAuthorizer<TAuthorizerContext> = APIGatewayProxyEventV2WithRequestContext<APIGatewayEventRequestContextV2WithAuthorizer<APIGatewayEventRequestContextLambdaAuthorizer<TAuthorizerContext>>>

external interface APIGatewayEventRequestContextV2WithAuthorizer<TAuthorizer> : APIGatewayEventRequestContextV2 {
    var authorizer: TAuthorizer
}

typealias APIGatewayProxyEventV2 = APIGatewayProxyEventV2WithRequestContext<APIGatewayEventRequestContextV2>

external interface APIGatewayProxyStructuredResultV2 {
    var statusCode: Number?
        get() = definedExternally
        set(value) = definedExternally
    var headers: `T$15`?
        get() = definedExternally
        set(value) = definedExternally
    var body: String?
        get() = definedExternally
        set(value) = definedExternally
    var isBase64Encoded: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var cookies: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

typealias ProxyHandler = APIGatewayProxyHandler

typealias ProxyCallback = APIGatewayProxyCallback

typealias APIGatewayEvent = APIGatewayProxyEvent

typealias ProxyResult = APIGatewayProxyResult