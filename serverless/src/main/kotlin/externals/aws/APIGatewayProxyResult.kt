package externals.aws


external interface APIGatewayProxyResult {
    var statusCode: Int
    var headers: Headers?
    var multiValueHeaders: MultiValuedHeaders?
    var body: String
    var isBase64Encoded: Boolean?
}

external interface Headers {

    operator fun get(header: String): dynamic /* Boolean? | Number? | String? */
    operator fun set(header: String, value: Boolean)
    operator fun set(header: String, value: Number)
    operator fun set(header: String, value: String)
}

external interface MultiValuedHeaders {
    operator fun get(header: String): Array<dynamic /* Boolean | Number | String */>?
    operator fun set(header: String, value: Array<dynamic /* Boolean | Number | String */>)
}
