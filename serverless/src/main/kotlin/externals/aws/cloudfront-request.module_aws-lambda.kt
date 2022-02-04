@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias CloudFrontRequestHandler = Handler<CloudFrontRequestEvent, dynamic /* externals.aws.CloudFrontResultResponse? | externals.aws.CloudFrontRequest? */>

typealias CloudFrontRequestCallback = Callback<dynamic /* externals.aws.CloudFrontResultResponse? | externals.aws.CloudFrontRequest? */>

external interface `T$39` {
    var cf: CloudFrontEvent /* externals.aws.CloudFrontEvent & `T$38` */
}

external interface CloudFrontRequestEvent {
    var Records: Array<`T$39`>
}