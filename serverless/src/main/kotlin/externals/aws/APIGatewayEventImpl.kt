package externals.aws

import kotlin.js.Json

data class APIGatewayEventImpl(
    override var path: String,
    override var body: String? = null,
    override var headers: APIGatewayProxyEventHeaders? = null,
    override var multiValueHeaders: APIGatewayProxyEventMultiValueHeaders? = null,
    override var httpMethod: String? = null,
    override var isBase64Encoded: Boolean? = null,
    override var pathParameters: APIGatewayProxyEventPathParameters? = null,
    override var queryStringParameters: APIGatewayProxyEventQueryStringParameters? = null,
    override var multiValueQueryStringParameters: APIGatewayProxyEventMultiValueQueryStringParameters? = null,
    override var stageVariables: APIGatewayProxyEventStageVariables? = null,
    override var requestContext: APIGatewayEventRequestContextWithAuthorizer<Json?>? = null,
    override var resource: String? = null
) : APIGatewayEvent

fun APIGatewayEvent(
    path: String,
    body: String? = null,
    headers: APIGatewayProxyEventHeaders? = null,
    multiValueHeaders: APIGatewayProxyEventMultiValueHeaders? = null,
    httpMethod: String? = null,
    isBase64Encoded: Boolean? = null,
    pathParameters: APIGatewayProxyEventPathParameters? = null,
    queryStringParameters: APIGatewayProxyEventQueryStringParameters? = null,
    multiValueQueryStringParameters: APIGatewayProxyEventMultiValueQueryStringParameters? = null,
    stageVariables: APIGatewayProxyEventStageVariables? = null,
    requestContext: APIGatewayEventRequestContextWithAuthorizer<Json?>? = null,
    resource: String? = null
): APIGatewayEvent = APIGatewayEventImpl(
    body = body,
    headers = headers,
    multiValueHeaders = multiValueHeaders,
    httpMethod = httpMethod,
    isBase64Encoded = isBase64Encoded,
    path = path,
    pathParameters = pathParameters,
    queryStringParameters = queryStringParameters,
    multiValueQueryStringParameters = multiValueQueryStringParameters,
    stageVariables = stageVariables,
    requestContext = requestContext,
    resource = resource
)
