@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")
package externals.aws

external interface ErrorConstructor {
    @nativeInvoke
    operator fun invoke(message: String = definedExternally): Error
    var prototype: Error
}

external interface ConcatArray<T> {
    var length: Number
    @nativeGetter
    operator fun get(n: Number): T?
    @nativeSetter
    operator fun set(n: Number, value: T)
    fun join(separator: String = definedExternally): String
    fun slice(start: Number = definedExternally, end: Number = definedExternally): Array<T>
}

external interface ArrayConstructor {
    fun <T> from(iterable: Iterable<T>): Array<T>
    fun <T> from(iterable: ArrayLike<T>): Array<T>
    fun <T, U> from(iterable: Iterable<T>, mapfn: (v: T, k: Number) -> U, thisArg: Any = definedExternally): Array<U>
    fun <T, U> from(iterable: Iterable<T>, mapfn: (v: T, k: Number) -> U): Array<U>
    fun <T, U> from(iterable: ArrayLike<T>, mapfn: (v: T, k: Number) -> U, thisArg: Any = definedExternally): Array<U>
    fun <T, U> from(iterable: ArrayLike<T>, mapfn: (v: T, k: Number) -> U): Array<U>
    fun <T> of(vararg items: T): Array<T>
    @nativeInvoke
    operator fun invoke(arrayLength: Number = definedExternally): Array<Any>
    @nativeInvoke
    operator fun invoke(): Array<Any>
    @nativeInvoke
    operator fun <T> invoke(arrayLength: Number): Array<T>
    @nativeInvoke
    operator fun <T> invoke(vararg items: T): Array<T>
    fun isArray(arg: Any): Boolean
    var prototype: Array<Any>
}

external interface PromiseLike<T> {
    fun then(onfulfilled: ((value: T) -> Any?)? = definedExternally, onrejected: ((reason: Any) -> Any?)? = definedExternally): PromiseLike<dynamic /* TResult1 | TResult2 */>
}

external interface ArrayLike<T> {
    var length: Number
    @nativeGetter
    operator fun get(n: Number): T?
    @nativeSetter
    operator fun set(n: Number, value: T)
}

typealias Pick<T, K> = Any

typealias Record<K, T> = Any

typealias Exclude<T, U> = Any