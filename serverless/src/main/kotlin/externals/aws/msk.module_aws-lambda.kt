@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias MSKHandler = Handler<MSKEvent, Unit>

external interface MSKRecord {
    var topic: String
    var partition: Number
    var offset: Number
    var timestamp: Number
    var timestampType: String /* "CREATE_TIME" | "LOG_APPEND_TIME" */
    var key: String
    var value: String
}

external interface `T$106` {
    @nativeGetter
    operator fun get(topic: String): Array<MSKRecord>?
    @nativeSetter
    operator fun set(topic: String, value: Array<MSKRecord>)
}

external interface MSKEvent {
    var eventSource: String /* "aws:kafka" */
    var eventSourceArn: String
    var records: `T$106`
}