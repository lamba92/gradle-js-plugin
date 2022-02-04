@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias ConnectContactFlowHandler = Handler<ConnectContactFlowEvent, ConnectContactFlowResult>

typealias ConnectContactFlowCallback = Callback<ConnectContactFlowResult>

external interface `T$85` {
    var Audio: dynamic /* externals.aws.StartedCustomerAudio? | externals.aws.StartedCustomerAudio & externals.aws.StoppedCustomerAudio */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$86` {
    var Customer: `T$85`
}

external interface `T$87` {
    var Attributes: `T$17`
    var Channel: String /* "VOICE" | "CHAT" */
    var ContactId: String
    var CustomerEndpoint: ConnectContactFlowEndpoint?
    var InitialContactId: String
    var InitiationMethod: String /* "INBOUND" | "OUTBOUND" | "TRANSFER" | "CALLBACK" | "API" */
    var InstanceARN: String
    var PreviousContactId: String
    var Queue: ConnectContactFlowQueue?
    var SystemEndpoint: ConnectContactFlowEndpoint?
    var MediaStreams: `T$86`
}

external interface `T$88` {
    var ContactData: `T$87`
    var Parameters: `T$17`
}

external interface ConnectContactFlowEvent {
    var Details: `T$88`
    var Name: String /* "ContactFlowEvent" */
}

external interface ConnectContactFlowEndpoint {
    var Address: String
    var Type: String /* "TELEPHONE_NUMBER" */
}

external interface ConnectContactFlowQueue {
    var ARN: String
    var Name: String
}

external interface ConnectContactFlowResult {
    @nativeGetter
    operator fun get(key: String): String?
    @nativeSetter
    operator fun set(key: String, value: String?)
}

external interface StartedCustomerAudio {
    var StartFragmentNumber: String
    var StartTimestamp: String
    var StreamARN: String
}

external interface StoppedCustomerAudio {
    var StopFragmentNumber: String
    var StopTimestamp: String
}