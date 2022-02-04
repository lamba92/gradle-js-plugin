@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$75` {
    var userAttributes: StringMap
    var userNotFound: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var validationData: StringMap?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PreAuthenticationTriggerEvent : BaseTriggerEvent<String /* "PreAuthentication_Authentication" */> {
}
