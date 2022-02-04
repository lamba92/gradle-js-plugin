@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$83` {
    var userAttributes: StringMap
    var privateChallengeParameters: StringMap
    var challengeAnswer: String
    var clientMetadata: StringMap?
        get() = definedExternally
        set(value) = definedExternally
    var userNotFound: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$84` {
    var answerCorrect: Boolean
}

external interface VerifyAuthChallengeResponseTriggerEvent :
    BaseTriggerEvent<String /* "VerifyAuthChallengeResponse_Authentication" */> {
}
