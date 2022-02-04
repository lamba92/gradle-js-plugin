@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias FirehoseTransformationHandler = Handler<FirehoseTransformationEvent, FirehoseTransformationResult>

typealias FirehoseTransformationCallback = Callback<FirehoseTransformationResult>

external interface FirehoseTransformationEvent {
    var invocationId: String
    var deliveryStreamArn: String
    var sourceKinesisStreamArn: String?
        get() = definedExternally
        set(value) = definedExternally
    var region: String
    var records: Array<FirehoseTransformationEventRecord>
}

external interface FirehoseTransformationEventRecord {
    var recordId: String
    var approximateArrivalTimestamp: Number
    var data: String
    var kinesisRecordMetadata: FirehoseRecordMetadata?
        get() = definedExternally
        set(value) = definedExternally
}

external interface FirehoseRecordMetadata {
    var shardId: String
    var partitionKey: String
    var approximateArrivalTimestamp: Number
    var sequenceNumber: String
    var subsequenceNumber: String
}

external interface FirehoseTransformationMetadata {
    var partitionKeys: `T$17`
}

external interface FirehoseTransformationResultRecord {
    var recordId: String
    var result: String /* "Ok" | "Dropped" | "ProcessingFailed" */
    var data: String
    var metadata: FirehoseTransformationMetadata?
        get() = definedExternally
        set(value) = definedExternally
}

external interface FirehoseTransformationResult {
    var records: Array<FirehoseTransformationResultRecord>
}