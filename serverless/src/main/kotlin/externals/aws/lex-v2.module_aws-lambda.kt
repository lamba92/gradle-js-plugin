@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias LexV2Handler = Handler<LexV2Event, LexV2Result>

typealias LexV2Callback = Callback<LexV2Result>

external interface LexV2Event {
    var messageVersion: String
    var invocationSource: String /* "DialogCodeHook" | "FulfillmentCodeHook" */
    var inputMode: String /* "DTMF" | "Speech" | "Text" */
    var responseContentType: String
    var sessionId: String
    var inputTranscript: String
    var bot: LexV2Bot
    var interpretations: Array<LexV2Interpretation>
    var requestAttributes: `T$17`
    var sessionState: LexV2SessionState
}

external interface LexV2Bot {
    var id: String
    var name: String
    var aliasId: String
    var aliasName: String
    var localeId: String
    var version: String
}

external interface LexV2Interpretation {
    var intent: LexV2Intent
    var nluConfidence: Number?
        get() = definedExternally
        set(value) = definedExternally
    var sentimentResponse: LexV2SentimentResponse?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$96` {
    @nativeGetter
    operator fun get(name: String): LexV2Slot?
    @nativeSetter
    operator fun set(name: String, value: LexV2Slot)
}

external interface LexV2Intent {
    var confirmationState: String /* "Confirmed" | "Denied" | "None" */
    var name: String
    var slots: `T$96`
    var state: String /* "Failed" | "Fulfilled" | "FulfillmentInProgress" | "InProgress" | "ReadyForFulfillment" | "Waiting" */
    var kendraResponse: Any?
        get() = definedExternally
        set(value) = definedExternally
}

external interface LexV2Slot {
    var shape: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: LexV2SlotValue
}

external interface LexV2SlotValue {
    var interpretedValue: String
    var originalValue: String
    var resolvedValues: Array<String>
}

external interface LexV2SentimentResponse {
    var sentiment: String
    var sentimentScore: LexV2SentimentScore
}

external interface LexV2SentimentScore {
    var mixed: Number
    var negative: Number
    var neutral: Number
    var positive: Number
}

external interface LexV2SessionState {
    var activeContexts: Array<LexV2ActiveContext>?
        get() = definedExternally
        set(value) = definedExternally
    var sessionAttributes: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var dialogAction: LexV2DialogAction?
        get() = definedExternally
        set(value) = definedExternally
    var intent: LexV2Intent
    var originatingRequestId: String
}

external interface `T$97` {
    var timeToLiveInSeconds: Number
    var turnsToLive: Number
}

external interface LexV2ActiveContext {
    var name: String
    var contextAttributes: `T$17`
    var timeToLive: `T$97`
}

external interface LexV2DialogAction {
    var slotToElicit: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String /* "Close" | "ConfirmIntent" | "Delegate" | "ElicitIntent" | "ElicitSlot" */
}

external interface LexV2ResultDialogAction : LexV2DialogAction {
    var slotElicitationStyle: String? /* "Default" | "SpellByLetter" | "SpellByWord" */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$98` {
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var state: String /* "Failed" | "Fulfilled" | "FulfillmentInProgress" | "InProgress" | "ReadyForFulfillment" | "Waiting" */
    var slots: `T$96`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$99` {
    var sessionAttributes: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var dialogAction: LexV2ResultDialogAction
    var intent: `T$98`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface LexV2Result {
    var sessionState: `T$99`
    var messages: Array<dynamic /* externals.aws.LexV2ContentMessage | externals.aws.LexV2ImageResponseCardMessage */>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface LexV2ContentMessage {
    var contentType: String /* "CustomPayload" | "PlainText" | "SSML" */
    var content: String
}

external interface LexV2ImageResponseCardMessage {
    var contentType: String /* "ImageResponseCard" */
    var imageResponseCard: LexV2ImageResponseCard
}

external interface LexV2ImageResponseCard {
    var title: String
    var subtitle: String?
        get() = definedExternally
        set(value) = definedExternally
    var imageUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var buttons: Array<LexV2ImageResponseCardButton>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface LexV2ImageResponseCardButton {
    var text: String
    var value: String
}