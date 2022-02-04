@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias SESHandler = Handler<SESEvent, Unit>

external interface SESMailHeader {
    var name: String
    var value: String
}

external interface SESMailCommonHeaders {
    var returnPath: String
    var from: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var date: String
    var to: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var cc: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var bcc: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var sender: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var replyTo: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var messageId: String
    var subject: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SESMail {
    var timestamp: String
    var source: String
    var messageId: String
    var destination: Array<String>
    var headersTruncated: Boolean
    var headers: Array<SESMailHeader>
    var commonHeaders: SESMailCommonHeaders
}

external interface SESReceiptStatus {
    var status: String /* "PASS" | "FAIL" | "GRAY" | "PROCESSING_FAILED" | "DISABLED" */
}

external interface SESReceiptS3Action {
    var type: String /* "S3" */
    var topicArn: String?
        get() = definedExternally
        set(value) = definedExternally
    var bucketName: String
    var objectKey: String
}

external interface SESReceiptSnsAction {
    var type: String /* "SNS" */
    var topicArn: String
}

external interface SESReceiptBounceAction {
    var type: String /* "Bounce" */
    var topicArn: String?
        get() = definedExternally
        set(value) = definedExternally
    var smtpReplyCode: String
    var statusCode: String
    var message: String
    var sender: String
}

external interface SESReceiptLambdaAction {
    var type: String /* "Lambda" */
    var topicArn: String?
        get() = definedExternally
        set(value) = definedExternally
    var functionArn: String
    var invocationType: String
}

external interface SESReceiptStopAction {
    var type: String /* "Stop" */
    var topicArn: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SESReceiptWorkMailAction {
    var type: String /* "WorkMail" */
    var topicArn: String?
        get() = definedExternally
        set(value) = definedExternally
    var organizationArn: String
}

external interface SESReceipt {
    var timestamp: String
    var processingTimeMillis: Number
    var recipients: Array<String>
    var spamVerdict: SESReceiptStatus
    var virusVerdict: SESReceiptStatus
    var spfVerdict: SESReceiptStatus
    var dkimVerdict: SESReceiptStatus
    var dmarcVerdict: SESReceiptStatus
    var dmarcPolicy: String? /* "none" | "quarantine" | "reject" */
        get() = definedExternally
        set(value) = definedExternally
    var action: dynamic /* externals.aws.SESReceiptS3Action | externals.aws.SESReceiptSnsAction | externals.aws.SESReceiptBounceAction | externals.aws.SESReceiptLambdaAction | externals.aws.SESReceiptStopAction | externals.aws.SESReceiptWorkMailAction */
        get() = definedExternally
        set(value) = definedExternally
}

external interface SESMessage {
    var mail: SESMail
    var receipt: SESReceipt
}

external interface SESEventRecord {
    var eventSource: String
    var eventVersion: String
    var ses: SESMessage
}

external interface SESEvent {
    var Records: Array<SESEventRecord>
}