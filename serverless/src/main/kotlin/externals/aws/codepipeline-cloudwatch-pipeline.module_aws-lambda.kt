@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias CodePipelineCloudWatchPipelineHandler = Handler<CodePipelineCloudWatchPipelineEvent, Unit>

external interface `T$57` {
    @nativeGetter
    operator fun get(key: String): String?
    @nativeSetter
    operator fun set(key: String, value: String)
    var pipeline: String
    var version: Number
    var state: String /* "STARTED" | "SUCCEEDED" | "RESUMED" | "FAILED" | "CANCELED" | "SUPERSEDED" */
}

external interface CodePipelineCloudWatchPipelineEvent {
    @nativeGetter
    operator fun get(key: String): String? /* "CodePipeline Pipeline Execution State Change" */
    @nativeSetter
    operator fun set(key: String, value: String /* "CodePipeline Pipeline Execution State Change" */)
    var version: String
    var id: String
    var source: String /* "aws.codepipeline" */
    var account: String
    var time: String
    var region: String
    var resources: Array<String>
    var detail: `T$57`
}