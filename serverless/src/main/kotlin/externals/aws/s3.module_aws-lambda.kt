@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias S3Handler = Handler<S3Event, Unit>

external interface S3EventRecordGlacierRestoreEventData {
    var lifecycleRestorationExpiryTime: String
    var lifecycleRestoreStorageClass: String
}

external interface S3EventRecordGlacierEventData {
    var restoreEventData: S3EventRecordGlacierRestoreEventData
}

external interface `T$100` {
    var principalId: String
}

external interface `T$101` {
    var sourceIPAddress: String
}

external interface `T$102` {
    @nativeGetter
    operator fun get(key: String): String?
    @nativeSetter
    operator fun set(key: String, value: String)
}

external interface `T$103` {
    var name: String
    var ownerIdentity: `T$100`
    var arn: String
}

external interface `T$104` {
    var key: String
    var size: Number
    var eTag: String
    var versionId: String?
        get() = definedExternally
        set(value) = definedExternally
    var sequencer: String
}

external interface `T$105` {
    var s3SchemaVersion: String
    var configurationId: String
    var bucket: `T$103`
    var `object`: `T$104`
}

external interface S3EventRecord {
    var eventVersion: String
    var eventSource: String
    var awsRegion: String
    var eventTime: String
    var eventName: String
    var userIdentity: `T$100`
    var requestParameters: `T$101`
    var responseElements: `T$102`
    var s3: `T$105`
    var glacierEventData: S3EventRecordGlacierEventData?
        get() = definedExternally
        set(value) = definedExternally
}

external interface S3Event {
    var Records: Array<S3EventRecord>
}

typealias S3CreateEvent = S3Event