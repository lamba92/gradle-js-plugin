@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

import kotlin.js.Json

typealias APIGatewayAuthorizerHandler = Handler<dynamic /* externals.aws.APIGatewayTokenAuthorizerEvent | externals.aws.APIGatewayRequestAuthorizerEvent */, APIGatewayAuthorizerResult>

typealias APIGatewayAuthorizerWithContextHandler<TAuthorizerContext> = Handler<dynamic /* externals.aws.APIGatewayTokenAuthorizerEvent | externals.aws.APIGatewayRequestAuthorizerEvent */, APIGatewayAuthorizerWithContextResult<TAuthorizerContext>>

typealias APIGatewayAuthorizerCallback = Callback<APIGatewayAuthorizerResult>

typealias APIGatewayAuthorizerWithContextCallback<TAuthorizerContext> = Callback<APIGatewayAuthorizerWithContextResult<TAuthorizerContext>>

typealias APIGatewayTokenAuthorizerHandler = Handler<APIGatewayTokenAuthorizerEvent, APIGatewayAuthorizerResult>

typealias APIGatewayTokenAuthorizerWithContextHandler<TAuthorizerContext> = Handler<APIGatewayTokenAuthorizerEvent, APIGatewayAuthorizerWithContextResult<TAuthorizerContext>>

typealias APIGatewayRequestAuthorizerHandler = Handler<APIGatewayRequestAuthorizerEvent, APIGatewayAuthorizerResult>

typealias APIGatewayRequestAuthorizerWithContextHandler<TAuthorizerContext> = Handler<APIGatewayRequestAuthorizerEvent, APIGatewayAuthorizerWithContextResult<TAuthorizerContext>>

external interface APIGatewayTokenAuthorizerEvent {
    var type: String /* "TOKEN" */
    var methodArn: String
    var authorizationToken: String
}

external interface APIGatewayRequestAuthorizerEventV2 {
    var version: String
    var type: String /* "REQUEST" */
    var routeArn: String
    var identitySource: Array<String>
    var routeKey: String
    var rawPath: String
    var rawQueryString: String
    var cookies: Array<String>
    var headers: APIGatewayRequestAuthorizerEventHeaders?
        get() = definedExternally
        set(value) = definedExternally
    var queryStringParameters: APIGatewayRequestAuthorizerEventQueryStringParameters?
        get() = definedExternally
        set(value) = definedExternally
    var requestContext: APIGatewayEventRequestContextV2
    var pathParameters: APIGatewayRequestAuthorizerEventPathParameters?
        get() = definedExternally
        set(value) = definedExternally
    var stageVariables: APIGatewayRequestAuthorizerEventStageVariables?
        get() = definedExternally
        set(value) = definedExternally
}

external interface APIGatewayRequestAuthorizerEventHeaders {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface APIGatewayRequestAuthorizerEventMultiValueHeaders {
    @nativeGetter
    operator fun get(name: String): Array<String>?
    @nativeSetter
    operator fun set(name: String, value: Array<String>?)
}

external interface APIGatewayRequestAuthorizerEventPathParameters {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface APIGatewayRequestAuthorizerEventQueryStringParameters {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface APIGatewayRequestAuthorizerEventMultiValueQueryStringParameters {
    @nativeGetter
    operator fun get(name: String): Array<String>?
    @nativeSetter
    operator fun set(name: String, value: Array<String>?)
}

external interface APIGatewayRequestAuthorizerEventStageVariables {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface APIGatewayRequestAuthorizerEvent {
    var type: String /* "REQUEST" */
    var methodArn: String
    var resource: String
    var path: String
    var httpMethod: String
    var headers: APIGatewayRequestAuthorizerEventHeaders?
    var multiValueHeaders: APIGatewayRequestAuthorizerEventMultiValueHeaders?
    var pathParameters: APIGatewayRequestAuthorizerEventPathParameters?
    var queryStringParameters: APIGatewayRequestAuthorizerEventQueryStringParameters?
    var multiValueQueryStringParameters: APIGatewayRequestAuthorizerEventMultiValueQueryStringParameters?
    var stageVariables: APIGatewayRequestAuthorizerEventStageVariables?
    var requestContext: APIGatewayEventRequestContextWithAuthorizer<Nothing?>
}

external interface APIGatewayAuthorizerResult {
    var principalId: String
    var policyDocument: PolicyDocument
    var context: APIGatewayAuthorizerResultContext?
        get() = definedExternally
        set(value) = definedExternally
    var usageIdentifierKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface APIGatewayAuthorizerWithContextResult<TAuthorizerContext : APIGatewayAuthorizerResultContext> {
    var principalId: String
    var policyDocument: PolicyDocument
    var context: TAuthorizerContext
    var usageIdentifierKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface APIGatewayIAMAuthorizerResult {
    var principalId: String
    var policyDocument: PolicyDocument
    var context: APIGatewayAuthorizerResultContext?
        get() = definedExternally
        set(value) = definedExternally
    var usageIdentifierKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface APIGatewayIAMAuthorizerWithContextResult<TAuthorizerContext : APIGatewayAuthorizerResultContext> {
    var principalId: String
    var policyDocument: PolicyDocument
    var context: TAuthorizerContext
    var usageIdentifierKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

typealias APIGatewayRequestIAMAuthorizerHandlerV2 = Handler<APIGatewayRequestAuthorizerEventV2, APIGatewayIAMAuthorizerResult>

typealias APIGatewayRequestIAMAuthorizerV2WithContextHandler<TAuthorizerContext> = Handler<APIGatewayRequestAuthorizerEventV2, APIGatewayIAMAuthorizerWithContextResult<TAuthorizerContext>>

external interface APIGatewaySimpleAuthorizerResult {
    var isAuthorized: Boolean
}

external interface APIGatewaySimpleAuthorizerWithContextResult<TAuthorizerContext> : APIGatewaySimpleAuthorizerResult {
    var context: TAuthorizerContext
}

typealias APIGatewayRequestSimpleAuthorizerHandlerV2 = Handler<APIGatewayRequestAuthorizerEventV2, APIGatewaySimpleAuthorizerResult>

typealias APIGatewayRequestSimpleAuthorizerHandlerV2WithContext<TAuthorizerContext> = Handler<APIGatewayRequestAuthorizerEventV2, APIGatewaySimpleAuthorizerWithContextResult<TAuthorizerContext>>

typealias CustomAuthorizerHandler = Handler<CustomAuthorizerEvent, APIGatewayAuthorizerResult>

typealias CustomAuthorizerCallback = APIGatewayAuthorizerCallback

external interface `T$17` {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String)
}

external interface `T$18` {
    @nativeGetter
    operator fun get(name: String): Array<String>?
    @nativeSetter
    operator fun set(name: String, value: Array<String>)
}

external interface `T$19` {
    @nativeGetter
    operator fun get(name: String): Array<String>?
    @nativeSetter
    operator fun set(name: String, value: Array<String>)
}

external interface CustomAuthorizerEvent {
    var type: String
    var methodArn: String
    var authorizationToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var resource: String?
        get() = definedExternally
        set(value) = definedExternally
    var path: String?
        get() = definedExternally
        set(value) = definedExternally
    var httpMethod: String?
        get() = definedExternally
        set(value) = definedExternally
    var headers: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var multiValueHeaders: `T$18`?
        get() = definedExternally
        set(value) = definedExternally
    var pathParameters: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var queryStringParameters: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var multiValueQueryStringParameters: `T$19`?
        get() = definedExternally
        set(value) = definedExternally
    var stageVariables: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var requestContext: APIGatewayEventRequestContextWithAuthorizer<Json?>?
        get() = definedExternally
        set(value) = definedExternally
    var domainName: String?
        get() = definedExternally
        set(value) = definedExternally
    var apiId: String?
        get() = definedExternally
        set(value) = definedExternally
}

typealias CustomAuthorizerResult = APIGatewayAuthorizerResult

typealias AuthResponse = APIGatewayAuthorizerResult

typealias AuthResponseContext = APIGatewayAuthorizerResultContext

external interface PolicyDocument {
    var Version: String
    var Id: String?
        get() = definedExternally
        set(value) = definedExternally
    var Statement: Array<BaseStatement /* externals.aws.BaseStatement & dynamic & dynamic */>
}

external interface ConditionBlock {
    @nativeGetter
    operator fun get(condition: String): dynamic /* externals.aws.Condition? | Array<externals.aws.Condition>? */
    @nativeSetter
    operator fun set(condition: String, value: Condition)
    @nativeSetter
    operator fun set(condition: String, value: Array<Condition>)
}

external interface Condition {
    @nativeGetter
    operator fun get(key: String): dynamic /* String? | Array<String>? */
    @nativeSetter
    operator fun set(key: String, value: String)
    @nativeSetter
    operator fun set(key: String, value: Array<String>)
}

external interface BaseStatement {
    var Effect: String
    var Sid: String?
        get() = definedExternally
        set(value) = definedExternally
    var Condition: ConditionBlock?
        get() = definedExternally
        set(value) = definedExternally
}

external interface MaybeStatementPrincipal {
    var Principal: dynamic /* `T$20`? | String? | Array<String>? */
        get() = definedExternally
        set(value) = definedExternally
    var NotPrincipal: dynamic /* `T$20`? | String? | Array<String>? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface MaybeStatementResource {
    var Resource: dynamic /* String? | Array<String>? */
        get() = definedExternally
        set(value) = definedExternally
    var NotResource: dynamic /* String? | Array<String>? */
        get() = definedExternally
        set(value) = definedExternally
}