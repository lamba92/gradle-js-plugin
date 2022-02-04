@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias ALBHandler = Handler<ALBEvent, ALBResult>

typealias ALBCallback = Callback<ALBResult>

external interface `T$14` {
    var targetGroupArn: String
}

external interface ALBEventRequestContext {
    var elb: `T$14`
}

external interface ALBEventQueryStringParameters {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface ALBEventHeaders {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface ALBEventMultiValueHeaders {
    @nativeGetter
    operator fun get(name: String): Array<String>?
    @nativeSetter
    operator fun set(name: String, value: Array<String>?)
}

external interface ALBEventMultiValueQueryStringParameters {
    @nativeGetter
    operator fun get(name: String): Array<String>?
    @nativeSetter
    operator fun set(name: String, value: Array<String>?)
}

external interface ALBEvent {
    var requestContext: ALBEventRequestContext
    var httpMethod: String
    var path: String
    var queryStringParameters: ALBEventQueryStringParameters?
        get() = definedExternally
        set(value) = definedExternally
    var headers: ALBEventHeaders?
        get() = definedExternally
        set(value) = definedExternally
    var multiValueQueryStringParameters: ALBEventMultiValueQueryStringParameters?
        get() = definedExternally
        set(value) = definedExternally
    var multiValueHeaders: ALBEventMultiValueHeaders?
        get() = definedExternally
        set(value) = definedExternally
    var body: String?
    var isBase64Encoded: Boolean
}

external interface `T$15` {
    @nativeGetter
    operator fun get(header: String): dynamic /* Boolean? | Number? | String? */
    @nativeSetter
    operator fun set(header: String, value: Boolean)
    @nativeSetter
    operator fun set(header: String, value: Number)
    @nativeSetter
    operator fun set(header: String, value: String)
}

external interface `T$16` {
    @nativeGetter
    operator fun get(header: String): Array<dynamic /* Boolean | Number | String */>?
    @nativeSetter
    operator fun set(header: String, value: Array<dynamic /* Boolean | Number | String */>)
}

external interface ALBResult {
    var statusCode: Number
    var statusDescription: String?
        get() = definedExternally
        set(value) = definedExternally
    var headers: `T$15`?
        get() = definedExternally
        set(value) = definedExternally
    var multiValueHeaders: `T$16`?
        get() = definedExternally
        set(value) = definedExternally
    var body: String?
        get() = definedExternally
        set(value) = definedExternally
    var isBase64Encoded: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}