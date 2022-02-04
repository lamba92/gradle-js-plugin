@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias LexHandler = Handler<LexEvent, LexResult>

typealias LexCallback = Callback<LexResult>

external interface LexEventSlots {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface LexEventSessionAttributes {
    @nativeGetter
    operator fun get(key: String): String?
    @nativeSetter
    operator fun set(key: String, value: String?)
}

external interface LexEventRequestAttributes {
    @nativeGetter
    operator fun get(key: String): String?
    @nativeSetter
    operator fun set(key: String, value: String?)
}

external interface `T$90` {
    var name: String
    var slots: LexEventSlots
    var slotDetails: LexSlotDetails
    var confirmationStatus: String /* "None" | "Confirmed" | "Denied" */
}

external interface `T$91` {
    var name: String
    var alias: String
    var version: String
}

external interface LexEvent {
    var currentIntent: `T$90`
    var bot: `T$91`
    var userId: String
    var inputTranscript: String
    var invocationSource: String /* "DialogCodeHook" | "FulfillmentCodeHook" */
    var outputDialogMode: String /* "Text" | "Voice" */
    var messageVersion: String /* "1.0" */
    var sessionAttributes: LexEventSessionAttributes
    var requestAttributes: LexEventRequestAttributes?
}

external interface LexSlotResolution {
    var value: String
}

external interface LexSlotDetail {
    var resolutions: dynamic /* JsTuple<externals.aws.LexSlotResolution, Any, Any, Any, Any> */
        get() = definedExternally
        set(value) = definedExternally
    var originalValue: String
}

external interface LexSlotDetails {
    @nativeGetter
    operator fun get(name: String): LexSlotDetail?
    @nativeSetter
    operator fun set(name: String, value: LexSlotDetail)
}

external interface `T$92` {
    var text: String
    var value: String
}

external interface LexGenericAttachment {
    var title: String
    var subTitle: String
    var imageUrl: String
    var attachmentLinkUrl: String
    var buttons: Array<`T$92`>
}

external interface `T$93` {
    var contentType: String /* "PlainText" | "SSML" | "CustomPayload" */
    var content: String
}

external interface `T$94` {
    var version: Number
    var contentType: String /* "application/vnd.amazonaws.card.generic" */
    var genericAttachments: Array<LexGenericAttachment>
}

external interface LexDialogActionBase {
    var type: String /* "Close" | "ElicitIntent" | "ElicitSlot" | "ConfirmIntent" */
    var message: `T$93`?
        get() = definedExternally
        set(value) = definedExternally
    var responseCard: `T$94`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface LexDialogActionClose : LexDialogActionBase {
    override var type: String /* "Close" */
    var fulfillmentState: String /* "Fulfilled" | "Failed" */
}

external interface LexDialogActionElicitIntent : LexDialogActionBase {
    override var type: String /* "ElicitIntent" */
}

external interface `T$95` {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String?)
}

external interface LexDialogActionElicitSlot : LexDialogActionBase {
    override var type: String /* "ElicitSlot" */
    var intentName: String
    var slots: `T$95`
    var slotToElicit: String
}

external interface LexDialogActionConfirmIntent : LexDialogActionBase {
    override var type: String /* "ConfirmIntent" */
    var intentName: String
    var slots: `T$95`
}

external interface LexDialogActionDelegate {
    var type: String /* "Delegate" */
    var slots: `T$95`
}

external interface LexResult {
    var sessionAttributes: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var dialogAction: dynamic /* externals.aws.LexDialogActionClose | externals.aws.LexDialogActionElicitIntent | externals.aws.LexDialogActionElicitSlot | externals.aws.LexDialogActionConfirmIntent | externals.aws.LexDialogActionDelegate */
        get() = definedExternally
        set(value) = definedExternally
}