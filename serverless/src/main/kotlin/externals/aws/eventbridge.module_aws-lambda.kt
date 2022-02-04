@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias EventBridgeHandler<TDetailType, TDetail, TResult> = Handler<EventBridgeEvent<TDetailType, TDetail>, TResult>

external interface EventBridgeEvent<TDetailType : String, TDetail> {
    @nativeGetter
    operator fun get(key: String): TDetailType?
    @nativeSetter
    operator fun set(key: String, value: TDetailType)
    var id: String
    var version: String
    var account: String
    var time: String
    var region: String
    var resources: Array<String>
    var source: String
    var detail: TDetail
}