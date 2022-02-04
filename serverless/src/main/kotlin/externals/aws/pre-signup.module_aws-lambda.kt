@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$76` {
    var userAttributes: StringMap
    var validationData: StringMap?
        get() = definedExternally
        set(value) = definedExternally
    var clientMetadata: StringMap?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$77` {
    var autoConfirmUser: Boolean
    var autoVerifyEmail: Boolean
    var autoVerifyPhone: Boolean
}

external interface BasePreSignUpTriggerEvent<T : String> : BaseTriggerEvent<T> {
}

typealias PreSignUpEmailTriggerEvent = BasePreSignUpTriggerEvent<String /* "PreSignUp_SignUp" */>

typealias PreSignUpExternalProviderTriggerEvent = BasePreSignUpTriggerEvent<String /* "PreSignUp_ExternalProvider" */>

typealias PreSignUpAdminCreateUserTriggerEvent = BasePreSignUpTriggerEvent<String /* "PreSignUp_AdminCreateUser" */>
