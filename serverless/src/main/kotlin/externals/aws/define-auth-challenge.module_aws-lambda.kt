@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$71` {
    var userAttributes: StringMap
    var session: Array<dynamic /* externals.aws.ChallengeResult | externals.aws.CustomChallengeResult */>
    var clientMetadata: StringMap?
        get() = definedExternally
        set(value) = definedExternally
    var userNotFound: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$72` {
    var challengeName: String
    var failAuthentication: Boolean
    var issueTokens: Boolean
}

external interface DefineAuthChallengeTriggerEvent :
    BaseTriggerEvent<String /* "DefineAuthChallenge_Authentication" */> {
}
