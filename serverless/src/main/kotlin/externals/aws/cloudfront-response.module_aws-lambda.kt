@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias CloudFrontResponseHandler = Handler<CloudFrontResponseEvent, CloudFrontResultResponse?>

typealias CloudFrontResponseCallback = Callback<CloudFrontResultResponse?>

external interface `T$41` {
    var cf: CloudFrontEvent /* externals.aws.CloudFrontEvent & `T$40` */
}

external interface CloudFrontResponseEvent {
    var Records: Array<`T$41`>
}