@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$74` {
    var userAttributes: StringMap
    var clientMetadata: StringMap?
        get() = definedExternally
        set(value) = definedExternally
}

external interface BasePostConfirmationTriggerEvent<T : String> : BaseTriggerEvent<T> {
}

typealias PostConfirmationConfirmSignUpTriggerEvent = BasePostConfirmationTriggerEvent<String /* "PostConfirmation_ConfirmSignUp" */>

typealias PostConfirmationConfirmForgotPassword = BasePostConfirmationTriggerEvent<String /* "PostConfirmation_ConfirmForgotPassword" */>
