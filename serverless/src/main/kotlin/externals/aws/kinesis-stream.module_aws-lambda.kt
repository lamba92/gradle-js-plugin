@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias KinesisStreamHandler = Handler<KinesisStreamEvent, Unit>

external interface KinesisStreamRecordPayload {
    var approximateArrivalTimestamp: Number
    var data: String
    var kinesisSchemaVersion: String
    var partitionKey: String
    var sequenceNumber: String
}

external interface KinesisStreamRecord {
    var awsRegion: String
    var eventID: String
    var eventName: String
    var eventSource: String
    var eventSourceARN: String
    var eventVersion: String
    var invokeIdentityArn: String
    var kinesis: KinesisStreamRecordPayload
}

external interface KinesisStreamEvent {
    var Records: Array<KinesisStreamRecord>
}