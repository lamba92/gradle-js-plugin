@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias DynamoDBStreamHandler = Handler<DynamoDBStreamEvent, Unit>

external interface `T$89` {
    @nativeGetter
    operator fun get(id: String): AttributeValue?
    @nativeSetter
    operator fun set(id: String, value: AttributeValue)
}

external interface AttributeValue {
    var B: String?
        get() = definedExternally
        set(value) = definedExternally
    var BS: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var BOOL: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var L: Array<AttributeValue>?
        get() = definedExternally
        set(value) = definedExternally
    var M: `T$89`?
        get() = definedExternally
        set(value) = definedExternally
    var N: String?
        get() = definedExternally
        set(value) = definedExternally
    var NS: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var NULL: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var S: String?
        get() = definedExternally
        set(value) = definedExternally
    var SS: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface StreamRecord {
    var ApproximateCreationDateTime: Number?
        get() = definedExternally
        set(value) = definedExternally
    var Keys: `T$89`?
        get() = definedExternally
        set(value) = definedExternally
    var NewImage: `T$89`?
        get() = definedExternally
        set(value) = definedExternally
    var OldImage: `T$89`?
        get() = definedExternally
        set(value) = definedExternally
    var SequenceNumber: String?
        get() = definedExternally
        set(value) = definedExternally
    var SizeBytes: Number?
        get() = definedExternally
        set(value) = definedExternally
    var StreamViewType: String? /* "KEYS_ONLY" | "NEW_IMAGE" | "OLD_IMAGE" | "NEW_AND_OLD_IMAGES" */
        get() = definedExternally
        set(value) = definedExternally
}

external interface DynamoDBRecord {
    var awsRegion: String?
        get() = definedExternally
        set(value) = definedExternally
    var dynamodb: StreamRecord?
        get() = definedExternally
        set(value) = definedExternally
    var eventID: String?
        get() = definedExternally
        set(value) = definedExternally
    var eventName: String? /* "INSERT" | "MODIFY" | "REMOVE" */
        get() = definedExternally
        set(value) = definedExternally
    var eventSource: String?
        get() = definedExternally
        set(value) = definedExternally
    var eventSourceARN: String?
        get() = definedExternally
        set(value) = definedExternally
    var eventVersion: String?
        get() = definedExternally
        set(value) = definedExternally
    var userIdentity: Any?
        get() = definedExternally
        set(value) = definedExternally
}

external interface DynamoDBStreamEvent {
    var Records: Array<DynamoDBRecord>
}