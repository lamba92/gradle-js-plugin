@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

import kotlin.js.Json

typealias CloudFormationCustomResourceHandler = Handler<dynamic /* externals.aws.CloudFormationCustomResourceCreateEvent | externals.aws.CloudFormationCustomResourceUpdateEvent | externals.aws.CloudFormationCustomResourceDeleteEvent */, Unit>

external interface `T$37` {
    var ServiceToken: String
    @nativeGetter
    operator fun get(Key: String): Any?
    @nativeSetter
    operator fun set(Key: String, value: Any)
}

external interface CloudFormationCustomResourceEventCommon {
    var ServiceToken: String
    var ResponseURL: String
    var StackId: String
    var RequestId: String
    var LogicalResourceId: String
    var ResourceType: String
    var ResourceProperties: `T$37`
}

external interface CloudFormationCustomResourceCreateEvent : CloudFormationCustomResourceEventCommon {
    var RequestType: String /* "Create" */
}

external interface CloudFormationCustomResourceUpdateEvent : CloudFormationCustomResourceEventCommon {
    var RequestType: String /* "Update" */
    var PhysicalResourceId: String
    var OldResourceProperties: Json
}

external interface CloudFormationCustomResourceDeleteEvent : CloudFormationCustomResourceEventCommon {
    var RequestType: String /* "Delete" */
    var PhysicalResourceId: String
}

external interface CloudFormationCustomResourceResponseCommon {
    var PhysicalResourceId: String
    var StackId: String
    var RequestId: String
    var LogicalResourceId: String
    var Data: Json?
        get() = definedExternally
        set(value) = definedExternally
    var NoEcho: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CloudFormationCustomResourceSuccessResponse : CloudFormationCustomResourceResponseCommon {
    var Status: String /* "SUCCESS" */
    var Reason: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CloudFormationCustomResourceFailedResponse : CloudFormationCustomResourceResponseCommon {
    var Status: String /* "FAILED" */
    var Reason: String
}