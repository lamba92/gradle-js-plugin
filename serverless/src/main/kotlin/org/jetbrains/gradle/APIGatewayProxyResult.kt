package org.jetbrains.gradle

import externals.aws.APIGatewayProxyResult
import externals.aws.Headers
import externals.aws.MultiValuedHeaders
import io.ktor.http.*

fun APIGatewayProxyResult(
    body: String,
    contentType: ContentType
) = APIGatewayProxyResult(
    statusCode = HttpStatusCode.OK,
    body = body,
    headers = mapOf(HttpHeaders.ContentType to contentType.toString())
)

fun APIGatewayProxyTextResult(body: String) =
    APIGatewayProxyResult(body, ContentType.Text.Plain)

fun APIGatewayProxyRedirectResult(url: String, statusCode: HttpStatusCode = HttpStatusCode.Found) =
    APIGatewayProxyResult(
        statusCode = statusCode,
        headers = mapOf("Location" to url)
    )

fun APIGatewayProxyResult(
    statusCode: HttpStatusCode,
    body: String = "",
    headers: Map<String, String> = emptyMap(),
    multiValueHeaders: Map<String, List<String>> = emptyMap(),
    isBase64Encoded: Boolean = false
): APIGatewayProxyResult = APIGatewayProxyResultImpl(
    statusCode = statusCode.value,
    headers = headers.toJsObject(),
    body = body,
    multiValueHeaders = multiValueHeaders.toJsObject(),
    isBase64Encoded = isBase64Encoded.asDynamic()
)

data class APIGatewayProxyResultImpl(
    override var statusCode: Int,
    override var body: String,
    override var headers: Headers? = emptyMap<String, String>().toJsObject(),
    override var isBase64Encoded: Boolean? = false,
    override var multiValueHeaders: MultiValuedHeaders? = emptyMap<String, Array<String>>().toJsObject()
) : APIGatewayProxyResult
