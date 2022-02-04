@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias ScheduledHandler<TDetail> = EventBridgeHandler<String /* "Scheduled Event" */, TDetail, Unit>

external interface ScheduledEvent<TDetail> : EventBridgeEvent<String /* "Scheduled Event" */, TDetail>