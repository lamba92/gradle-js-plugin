@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface GroupOverrideDetails {
    var groupsToOverride: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var iamRolesToOverride: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var preferredRole: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$78` {
    var userAttributes: StringMap
    var groupConfiguration: GroupOverrideDetails
    var clientMetadata: StringMap?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$79` {
    var claimsToAddOrOverride: StringMap?
        get() = definedExternally
        set(value) = definedExternally
    var claimsToSuppress: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var groupOverrideDetails: GroupOverrideDetails?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$80` {
    var claimsOverrideDetails: `T$79`
}

external interface BasePreTokenGenerationTriggerEvent<T : String> : BaseTriggerEvent<T> {
}

typealias PreTokenGenerationHostedAuthTriggerEvent = BasePreTokenGenerationTriggerEvent<String /* "TokenGeneration_HostedAuth" */>

typealias PreTokenGenerationAuthenticationTriggerEvent = BasePreTokenGenerationTriggerEvent<String /* "TokenGeneration_Authentication" */>

typealias PreTokenGenerationNewPasswordChallengeTriggerEvent = BasePreTokenGenerationTriggerEvent<String /* "TokenGeneration_NewPasswordChallenge" */>

typealias PreTokenGenerationAuthenticateDeviceTriggerEvent = BasePreTokenGenerationTriggerEvent<String /* "TokenGeneration_AuthenticateDevice" */>

typealias PreTokenGenerationRefreshTokensTriggerEvent = BasePreTokenGenerationTriggerEvent<String /* "TokenGeneration_RefreshTokens" */>
