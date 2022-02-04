@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

import kotlin.js.Json

typealias AppSyncResolverHandler<TArguments, TResult, TSource> = Handler<AppSyncResolverEvent<TArguments, TSource>, TResult>

typealias AppSyncBatchResolverHandler<TArguments, TResult, TSource> = Handler<Array<AppSyncResolverEvent<TArguments, TSource>>, Array<TResult>>

typealias AppSyncAuthorizerHander<TResolverContext> = AppSyncAuthorizerHandler<TResolverContext>

typealias AppSyncAuthorizerHandler<TResolverContext> = Handler<AppSyncAuthorizerEvent, AppSyncAuthorizerResult<TResolverContext>>

external interface AppSyncResolverEventHeaders {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface `T$32` {
    var headers: AppSyncResolverEventHeaders
}

external interface `T$33` {
    var selectionSetList: Array<String>
    var selectionSetGraphQL: String
    var parentTypeName: String
    var fieldName: String
    var variables: Json
}

external interface `T$34` {
    var result: Json
}

external interface AppSyncResolverEvent<TArguments, TSource> {
    var arguments: TArguments
    var identity: dynamic /* externals.aws.AppSyncIdentityIAM? | externals.aws.AppSyncIdentityCognito? | externals.aws.AppSyncIdentityOIDC? | externals.aws.AppSyncIdentityLambda? */
        get() = definedExternally
        set(value) = definedExternally
    var source: TSource
    var request: `T$32`
    var info: `T$33`
    var prev: `T$34`?
    var stash: Json
}

external interface `T$35` {
    var apiId: String
    var accountId: String
    var requestId: String
    var queryString: String
    var variables: Json
}

external interface AppSyncAuthorizerEvent {
    var authorizationToken: String
    var requestContext: `T$35`
}

external interface AppSyncAuthorizerResult<TResolverContext> {
    var isAuthorized: Boolean
    var resolverContext: TResolverContext?
        get() = definedExternally
        set(value) = definedExternally
    var deniedFields: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var ttlOverride: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface AppSyncIdentityIAM {
    var accountId: String
    var cognitoIdentityPoolId: String
    var cognitoIdentityId: String
    var sourceIp: Array<String>
    var username: String
    var userArn: String
    var cognitoIdentityAuthType: String
    var cognitoIdentityAuthProvider: String
}

external interface AppSyncIdentityCognito {
    var sub: String
    var issuer: String
    var username: String
    var claims: Any
    var sourceIp: Array<String>
    var defaultAuthStrategy: String
    var groups: Array<String>?
}

external interface AppSyncIdentityOIDC {
    var claims: Any
    var issuer: String
    var sub: String
}

external interface AppSyncIdentityLambda {
    var resolverContext: Any
}