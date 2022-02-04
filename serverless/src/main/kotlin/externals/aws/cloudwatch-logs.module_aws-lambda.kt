@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias CloudWatchLogsHandler = Handler<CloudWatchLogsEvent, Unit>

external interface CloudWatchLogsEvent {
    var awslogs: CloudWatchLogsEventData
}

external interface CloudWatchLogsEventData {
    var data: String
}

external interface CloudWatchLogsDecodedData {
    var owner: String
    var logGroup: String
    var logStream: String
    var subscriptionFilters: Array<String>
    var messageType: String
    var logEvents: Array<CloudWatchLogsLogEvent>
}

external interface CloudWatchLogsLogEventExtractedFields {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface CloudWatchLogsLogEvent {
    var id: String
    var timestamp: Number
    var message: String
    var extractedFields: CloudWatchLogsLogEventExtractedFields?
        get() = definedExternally
        set(value) = definedExternally
}