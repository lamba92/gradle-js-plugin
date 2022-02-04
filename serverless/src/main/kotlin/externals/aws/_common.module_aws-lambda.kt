@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface StringMap {
    @nativeGetter
    operator fun get(name: String): String?
    @nativeSetter
    operator fun set(name: String, value: String)
}

external interface ChallengeResult {
    var challengeName: String /* "PASSWORD_VERIFIER" | "SMS_MFA" | "DEVICE_SRP_AUTH" | "DEVICE_PASSWORD_VERIFIER" | "ADMIN_NO_SRP_AUTH" | "SRP_A" */
    var challengeResult: Boolean
    var challengeMetadata: Any?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CustomChallengeResult {
    var challengeName: String /* "CUSTOM_CHALLENGE" */
    var challengeResult: Boolean
    var challengeMetadata: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface BaseTriggerEvent<T : String> {
    var version: String
    var region: String
    var userPoolId: String
    var triggerSource: T
    var userName: String
    var callerContext: `T$59`
    var request: Any
    var response: Any
}