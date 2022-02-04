@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias S3BatchHandler = Handler<S3BatchEvent, S3BatchResult>

typealias S3BatchCallback = Callback<S3BatchResult>

external interface S3BatchEvent {
    var invocationSchemaVersion: String
    var invocationId: String
    var job: S3BatchEventJob
    var tasks: Array<S3BatchEventTask>
}

external interface S3BatchEventJob {
    var id: String
}

external interface S3BatchEventTask {
    var taskId: String
    var s3Key: String
    var s3VersionId: String?
    var s3BucketArn: String
}

external interface S3BatchResult {
    var invocationSchemaVersion: String
    var treatMissingKeysAs: String /* "Succeeded" | "TemporaryFailure" | "PermanentFailure" */
    var invocationId: String
    var results: Array<S3BatchResultResult>
}

external interface S3BatchResultResult {
    var taskId: String
    var resultCode: String /* "Succeeded" | "TemporaryFailure" | "PermanentFailure" */
    var resultString: String
}