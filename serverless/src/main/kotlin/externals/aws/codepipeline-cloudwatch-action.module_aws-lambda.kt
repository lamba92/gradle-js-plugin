@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias CodePipelineCloudWatchActionHandler = Handler<CodePipelineCloudWatchActionEvent, Unit>

external interface `T$55` {
    var owner: String /* "AWS" | "Custom" | "ThirdParty" */
    var category: String /* "Approval" | "Build" | "Deploy" | "Invoke" | "Source" | "Test" */
    var provider: String
    var version: Number
}

external interface `T$56` {
    @nativeGetter
    operator fun get(key: String): String?
    @nativeSetter
    operator fun set(key: String, value: String)
    var pipeline: String
    var version: Number
    var stage: String
    var action: String
    var state: String /* "STARTED" | "SUCCEEDED" | "FAILED" | "CANCELED" */
    var type: `T$55`
}

external interface CodePipelineCloudWatchActionEvent {
    @nativeGetter
    operator fun get(key: String): String? /* "CodePipeline Action Execution State Change" */
    @nativeSetter
    operator fun set(key: String, value: String /* "CodePipeline Action Execution State Change" */)
    var version: String
    var id: String
    var source: String /* "aws.codepipeline" */
    var account: String
    var time: String
    var region: String
    var resources: Array<String>
    var detail: `T$56`
}