@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias SQSHandler = Handler<SQSEvent, dynamic /* externals.aws.SQSBatchResponse | Unit */>

external interface SQSRecord {
    var messageId: String
    var receiptHandle: String
    var body: String
    var attributes: SQSRecordAttributes
    var messageAttributes: SQSMessageAttributes
    var md5OfBody: String
    var eventSource: String
    var eventSourceARN: String
    var awsRegion: String
}

external interface SQSEvent {
    var Records: Array<SQSRecord>
}

external interface SQSRecordAttributes {
    var AWSTraceHeader: String?
        get() = definedExternally
        set(value) = definedExternally
    var ApproximateReceiveCount: String
    var SentTimestamp: String
    var SenderId: String
    var ApproximateFirstReceiveTimestamp: String
    var SequenceNumber: String?
        get() = definedExternally
        set(value) = definedExternally
    var MessageGroupId: String?
        get() = definedExternally
        set(value) = definedExternally
    var MessageDeduplicationId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SQSMessageAttribute {
    var stringValue: String?
        get() = definedExternally
        set(value) = definedExternally
    var binaryValue: String?
        get() = definedExternally
        set(value) = definedExternally
    var stringListValues: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var binaryListValues: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var dataType: String /* "String" | "Number" | "Binary" | String */
}

external interface SQSMessageAttributes {
    @nativeGetter
    operator fun get(name: String): SQSMessageAttribute?
    @nativeSetter
    operator fun set(name: String, value: SQSMessageAttribute)
}

external interface SQSBatchResponse {
    var batchItemFailures: Array<SQSBatchItemFailure>
}

external interface SQSBatchItemFailure {
    var itemIdentifier: String
}