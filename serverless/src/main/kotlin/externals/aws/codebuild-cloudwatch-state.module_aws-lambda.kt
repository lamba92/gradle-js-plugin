@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias CodeBuildCloudWatchStateHandler = EventBridgeHandler<String /* "CodeBuild Build State Change" */, CodeBuildStateEventDetail, Unit>

external interface `T$42` {
    var type: String /* "NO_CACHE" | "LOCAL" | "S3" */
}

external interface `T$43` {
    var buildspec: String
    var location: String
    var type: String /* "CODECOMMIT" | "CODEPIPELINE" | "GITHUB" | "GITHUB_ENTERPRISE" | "BITBUCKET" | "S3" | "NO_SOURCE" */
}

external interface `T$44` {
    var location: String
}

external interface `T$45` {
    var name: String
    var type: String? /* "PARAMETER_STORE" | "PLAINTEXT" | "SECRETS_MANAGER" */
        get() = definedExternally
        set(value) = definedExternally
    var value: String
}

external interface `T$46` {
    @nativeGetter
    operator fun get(key: String): Any?
    @nativeSetter
    operator fun set(key: String, value: Boolean)
    @nativeSetter
    operator fun set(key: String, value: String? /* "CODEBUILD" | "SERVICE_ROLE" */)
    @nativeSetter
    operator fun set(key: String, value: String /* "BUILD_GENERAL1_SMALL" | "BUILD_GENERAL1_MEDIUM" | "BUILD_GENERAL1_LARGE" | "BUILD_GENERAL1_2XLARGE" */)
    @nativeSetter
    operator fun set(key: String, value: Array<`T$45`>)
    var image: String
    var type: String /* "LINUX_CONTAINER" | "LINUX_GPU_CONTAINER" | "WINDOWS_CONTAINER" | "ARM_CONTAINER" */
}

external interface `T$47` {
    @nativeGetter
    operator fun get(key: String): String?
    @nativeSetter
    operator fun set(key: String, value: String)
}

external interface `T$48` {
    @nativeGetter
    operator fun get(key: String): Any?
    @nativeSetter
    operator fun set(key: String, value: Array<String>?)
    @nativeSetter
    operator fun set(key: String, value: String)
    @nativeSetter
    operator fun set(key: String, value: String?)
    @nativeSetter
    operator fun set(key: String, value: Number?)
}

external interface `T$49` {
    @nativeGetter
    operator fun get(key: String): Any?
    @nativeSetter
    operator fun set(key: String, value: Number)
    @nativeSetter
    operator fun set(key: String, value: Boolean)
    @nativeSetter
    operator fun set(key: String, value: String)
    @nativeSetter
    operator fun set(key: String, value: dynamic /* JsTuple<> */)
    var cache: `T$42`
    var initiator: String
    var source: `T$43`
    var artifact: `T$44`
    var environment: `T$46`
    var logs: `T$47`
    var phases: Array<`T$48`>
}

external interface CodeBuildStateEventDetail {
    @nativeGetter
    operator fun get(key: String): Any?
    @nativeSetter
    operator fun set(key: String, value: String /* "IN_PROGRESS" | "SUCCEEDED" | "FAILED" | "STOPPED" */)
    @nativeSetter
    operator fun set(key: String, value: `T$49`)
    var version: String
}

external interface CodeBuildCloudWatchStateEvent :
    EventBridgeEvent<String /* "CodeBuild Build State Change" */, CodeBuildStateEventDetail> {
    override var source: String /* "aws.codebuild" */
}