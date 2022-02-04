package org.jetbrains.gradle

external interface FileOptions : TmpNameOptions {
    var detachDescriptor: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var discardDescriptor: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var keep: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var mode: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TmpNameOptions {
    var dir: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var prefix: String?
        get() = definedExternally
        set(value) = definedExternally
    var postfix: String?
        get() = definedExternally
        set(value) = definedExternally
    var template: String?
        get() = definedExternally
        set(value) = definedExternally
    var tmpdir: String?
        get() = definedExternally
        set(value) = definedExternally
    var tries: Number?
        get() = definedExternally
        set(value) = definedExternally
}