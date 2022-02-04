@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

import kotlin.js.Json

typealias CdkCustomResourceHandler = Handler<dynamic /* externals.aws.CloudFormationCustomResourceCreateEvent | externals.aws.CloudFormationCustomResourceUpdateEvent | externals.aws.CloudFormationCustomResourceDeleteEvent */, CdkCustomResourceResponse>

typealias CdkCustomResourceCallback = Callback<CdkCustomResourceResponse>

external interface CdkCustomResourceResponse {
    var PhysicalResourceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var Data: Json?
        get() = definedExternally
        set(value) = definedExternally
    @nativeGetter
    operator fun get(Key: String): Any?
    @nativeSetter
    operator fun set(Key: String, value: Any)
}

external interface CdkCustomResourceIsCompleteResponseSuccess {
    var IsComplete: Boolean
    var Data: Json?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CdkCustomResourceIsCompleteResponseWaiting {
    var IsComplete: Boolean
}

typealias CdkCustomResourceIsCompleteHandler = Handler<dynamic /* externals.aws.CloudFormationCustomResourceCreateEvent | externals.aws.CloudFormationCustomResourceUpdateEvent | externals.aws.CloudFormationCustomResourceDeleteEvent */, dynamic /* externals.aws.CdkCustomResourceIsCompleteResponseSuccess | externals.aws.CdkCustomResourceIsCompleteResponseWaiting */>

typealias CdkCustomResourceIsCompleteCallback = Callback<dynamic /* externals.aws.CdkCustomResourceIsCompleteResponseSuccess | externals.aws.CdkCustomResourceIsCompleteResponseWaiting */>