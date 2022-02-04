@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$65` {
    var userAttributes: StringMap
    var challengeName: String
    var session: Array<dynamic /* externals.aws.ChallengeResult | externals.aws.CustomChallengeResult */>
    var clientMetadata: StringMap?
        get() = definedExternally
        set(value) = definedExternally
    var userNotFound: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$66` {
    var publicChallengeParameters: StringMap
    var privateChallengeParameters: StringMap
    var challengeMetadata: String
}

external interface CreateAuthChallengeTriggerEvent :
    BaseTriggerEvent<String /* "CreateAuthChallenge_Authentication" */> {
}
