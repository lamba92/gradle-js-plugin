@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$69` {
    var type: String
    var code: String?
    var userAttributes: StringMap
    var clientMetadata: StringMap?
        get() = definedExternally
        set(value) = definedExternally
}

external interface BaseCustomEmailSenderTriggerEvent<T : String> : BaseTriggerEvent<T> {
}

external interface CustomEmailSender_AccountTakeOverNotification_UserAttributes {
    var EVENT_ID: String
    var USER_NAME: String
    var IP_ADDRESS: String
    var ACCOUNT_TAKE_OVER_ACTION: String /* "BLOCK" | "NO_ACTION" | "MFA" | "MFA_IF_CONFIGURED" | "MFA_REQUIRED" */
    var ONE_CLICK_LINK_VALID: String
    var ONE_CLICK_LINK_INVALID: String
    var LOGIN_TIME: String
    var FEEDBACK_TOKEN: String
    var CITY: String?
        get() = definedExternally
        set(value) = definedExternally
    var COUNTRY: String?
        get() = definedExternally
        set(value) = definedExternally
    var DEVICE_NAME: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CustomEmailSenderSignUpTriggerEvent :
    BaseCustomEmailSenderTriggerEvent<String /* "CustomEmailSender_SignUp" */>

external interface CustomEmailSenderResendCodeTriggerEvent :
    BaseCustomEmailSenderTriggerEvent<String /* "CustomEmailSender_ResendCode" */>

external interface CustomEmailSenderForgotPasswordTriggerEvent :
    BaseCustomEmailSenderTriggerEvent<String /* "CustomEmailSender_ForgotPassword" */>

external interface CustomEmailSenderUpdateUserAttributeTriggerEvent :
    BaseCustomEmailSenderTriggerEvent<String /* "CustomEmailSender_UpdateUserAttribute" */>

external interface CustomEmailSenderVerifyUserAttributeTriggerEvent :
    BaseCustomEmailSenderTriggerEvent<String /* "CustomEmailSender_VerifyUserAttribute" */>

external interface CustomEmailSenderAdminCreateUserTriggerEvent :
    BaseCustomEmailSenderTriggerEvent<String /* "CustomEmailSender_AdminCreateUser" */>

external interface `T$70` {
    var type: String
    var code: String?
    var userAttributes: CustomEmailSender_AccountTakeOverNotification_UserAttributes
    var clientMetadata: StringMap?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CustomEmailSenderAccountTakeOverNotificationTriggerEvent :
    BaseTriggerEvent<String /* "CustomEmailSender_AccountTakeOverNotification" */> {
}
