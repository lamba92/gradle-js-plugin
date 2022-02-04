@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$73` {
    var userAttributes: StringMap
    var newDeviceUsed: Boolean
    var clientMetadata: StringMap?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PostAuthenticationTriggerEvent : BaseTriggerEvent<String /* "PostAuthentication_Authentication" */> {
}
