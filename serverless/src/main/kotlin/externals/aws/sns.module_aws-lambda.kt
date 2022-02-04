@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias SNSHandler = Handler<SNSEvent, Unit>

external interface SNSMessageAttribute {
    var Type: String
    var Value: String
}

external interface SNSMessageAttributes {
    @nativeGetter
    operator fun get(name: String): SNSMessageAttribute?
    @nativeSetter
    operator fun set(name: String, value: SNSMessageAttribute)
}

external interface SNSMessage {
    var SignatureVersion: String
    var Timestamp: String
    var Signature: String
    var SigningCertUrl: String
    var MessageId: String
    var Message: String
    var MessageAttributes: SNSMessageAttributes
    var Type: String
    var UnsubscribeUrl: String
    var TopicArn: String
    var Subject: String
}

external interface SNSEventRecord {
    var EventVersion: String
    var EventSubscriptionArn: String
    var EventSource: String
    var Sns: SNSMessage
}

external interface SNSEvent {
    var Records: Array<SNSEventRecord>
}