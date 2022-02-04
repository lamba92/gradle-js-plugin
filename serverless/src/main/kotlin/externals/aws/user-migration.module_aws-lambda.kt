@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$81` {
    var password: String
    var validationData: StringMap?
        get() = definedExternally
        set(value) = definedExternally
    var clientMetadata: StringMap?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$82` {
    var userAttributes: StringMap
    var finalUserStatus: String? /* "UNCONFIRMED" | "CONFIRMED" | "ARCHIVED" | "COMPROMISED" | "UNKNOWN" | "RESET_REQUIRED" | "FORCE_CHANGE_PASSWORD" */
        get() = definedExternally
        set(value) = definedExternally
    var messageAction: String? /* "RESEND" | "SUPPRESS" */
        get() = definedExternally
        set(value) = definedExternally
    var desiredDeliveryMediums: Array<String /* "SMS" | "EMAIL" */>
    var forceAliasCreation: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface BaseUserMigrationTriggerEvent<T : String> : BaseTriggerEvent<T> {
}

typealias UserMigrationAuthenticationTriggerEvent = BaseUserMigrationTriggerEvent<String /* "UserMigration_Authentication" */>

typealias UserMigrationForgotPasswordTriggerEvent = BaseUserMigrationTriggerEvent<String /* "UserMigration_ForgotPassword" */>
