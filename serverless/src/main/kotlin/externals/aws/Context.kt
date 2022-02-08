package externals.aws

external interface Context {
    var callbackWaitsForEmptyEventLoop: Boolean
    var functionName: String
    var functionVersion: String
    var invokedFunctionArn: String
    var memoryLimitInMB: String
    var awsRequestId: String
    var logGroupName: String
    var logStreamName: String
    var identity: CognitoIdentity?
        get() = definedExternally
        set(value) = definedExternally
    var clientContext: ClientContext?
        get() = definedExternally
        set(value) = definedExternally
    fun getRemainingTimeInMillis(): Number
    fun done(error: Throwable = definedExternally, result: Any = definedExternally)
    fun fail(error: Throwable)
    fun fail(error: String)
    fun succeed(messageOrObject: Any)
    fun succeed(message: String, obj: Any)
}

external interface CognitoIdentity {
    var cognitoIdentityId: String
    var cognitoIdentityPoolId: String
}

external interface ClientContext {
    var client: ClientContextClient
    var Custom: Any?
        get() = definedExternally
        set(value) = definedExternally
    var env: ClientContextEnv
}

external interface ClientContextClient {
    var installationId: String
    var appTitle: String
    var appVersionName: String
    var appVersionCode: String
    var appPackageName: String
}

external interface ClientContextEnv {
    var platformVersion: String
    var platform: String
    var make: String
    var model: String
    var locale: String
}
