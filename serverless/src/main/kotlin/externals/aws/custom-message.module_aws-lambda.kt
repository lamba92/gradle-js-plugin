@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$67` {
    var userAttributes: StringMap
    var codeParameter: String
    var usernameParameter: String
    var clientMetadata: StringMap?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$68` {
    var smsMessage: String
    var emailMessage: String
    var emailSubject: String
}

external interface BaseCustomMessageTriggerEvent<T : String> : BaseTriggerEvent<T> {
}

typealias CustomMessageAdminCreateUserTriggerEvent = BaseCustomMessageTriggerEvent<String /* "CustomMessage_AdminCreateUser" */>

typealias CustomMessageAuthenticationTriggerEvent = BaseCustomMessageTriggerEvent<String /* "CustomMessage_Authentication" */>

typealias CustomMessageForgotPasswordTriggerEvent = BaseCustomMessageTriggerEvent<String /* "CustomMessage_ForgotPassword" */>

typealias CustomMessageResendCodeTriggerEvent = BaseCustomMessageTriggerEvent<String /* "CustomMessage_ResendCode" */>

typealias CustomMessageSignUpTriggerEvent = BaseCustomMessageTriggerEvent<String /* "CustomMessage_SignUp" */>

typealias CustomMessageUpdateUserAttributeTriggerEvent = BaseCustomMessageTriggerEvent<String /* "CustomMessage_UpdateUserAttribute" */>

typealias CustomMessageVerifyUserAttributeTriggerEvent = BaseCustomMessageTriggerEvent<String /* "CustomMessage_VerifyUserAttribute" */>
