@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)
@file:JsModule("semver")

package externals.semver

external fun parse(version: String?, optionsOrLoose: Boolean = definedExternally): SemVer?

external fun parse(version: String?): SemVer?

external fun parse(version: String?, optionsOrLoose: Options = definedExternally): SemVer?

external fun parse(version: SemVer?, optionsOrLoose: Boolean = definedExternally): SemVer?

external fun parse(version: SemVer?): SemVer?

external fun parse(version: SemVer?, optionsOrLoose: Options = definedExternally): SemVer?

open external class SemVer {
    constructor(version: String, optionsOrLoose: Boolean = definedExternally)
    constructor(version: String)
    constructor(version: String, optionsOrLoose: Options = definedExternally)
    constructor(version: SemVer, optionsOrLoose: Boolean = definedExternally)
    constructor(version: SemVer)
    constructor(version: SemVer, optionsOrLoose: Options = definedExternally)
    open var raw: String
    open var loose: Boolean
    open var options: Options
    open fun format(): String
    open fun inspect(): String
    open var major: Number
    open var minor: Number
    open var patch: Number
    open var version: String
    open var build: Array<String>
    open var prerelease: Array<dynamic /* String | Number */>
    open fun compare(other: String): dynamic /* 1 | 0 | "-1" */
    open fun compare(other: SemVer): dynamic /* 1 | 0 | "-1" */
    open fun compareMain(other: String): dynamic /* 1 | 0 | "-1" */
    open fun compareMain(other: SemVer): dynamic /* 1 | 0 | "-1" */
    open fun comparePre(other: String): dynamic /* 1 | 0 | "-1" */
    open fun comparePre(other: SemVer): dynamic /* 1 | 0 | "-1" */
    open fun compareBuild(other: String): dynamic /* 1 | 0 | "-1" */
    open fun compareBuild(other: SemVer): dynamic /* 1 | 0 | "-1" */
    open fun inc(release: String /* "major" | "premajor" | "minor" | "preminor" | "patch" | "prepatch" | "prerelease" */, identifier: String = definedExternally): SemVer
}

external var SEMVER_SPEC_VERSION: String /* "2.0.0" */

external interface Options {
    var loose: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var includePrerelease: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CoerceOptions : Options {
    var rtl: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

open external class Range {
    constructor(range: String, optionsOrLoose: Boolean = definedExternally)
    constructor(range: String)
    constructor(range: String, optionsOrLoose: Options = definedExternally)
    constructor(range: Range, optionsOrLoose: Boolean = definedExternally)
    constructor(range: Range)
    constructor(range: Range, optionsOrLoose: Options = definedExternally)
    open var range: String
    open var raw: String
    open var loose: Boolean
    open var options: Options
    open var includePrerelease: Boolean
    open fun format(): String
    open fun inspect(): String
    open var set: Array<Array<Comparator>>
    open fun parseRange(range: String): Array<Comparator>
    open fun test(version: String): Boolean
    open fun test(version: SemVer): Boolean
    open fun intersects(range: Range, optionsOrLoose: Boolean = definedExternally): Boolean
    open fun intersects(range: Range): Boolean
    open fun intersects(range: Range, optionsOrLoose: Options = definedExternally): Boolean
}

open external class Comparator {
    constructor(comp: String, optionsOrLoose: Boolean = definedExternally)
    constructor(comp: String)
    constructor(comp: String, optionsOrLoose: Options = definedExternally)
    constructor(comp: Comparator, optionsOrLoose: Boolean = definedExternally)
    constructor(comp: Comparator)
    constructor(comp: Comparator, optionsOrLoose: Options = definedExternally)
    open var semver: SemVer
    open var operator: String /* "" | "=" | "<" | ">" | "<=" | ">=" */
    open var value: String
    open var loose: Boolean
    open var options: Options
    open fun parse(comp: String)
    open fun test(version: String): Boolean
    open fun test(version: SemVer): Boolean
    open fun intersects(comp: Comparator, optionsOrLoose: Boolean = definedExternally): Boolean
    open fun intersects(comp: Comparator): Boolean
    open fun intersects(comp: Comparator, optionsOrLoose: Options = definedExternally): Boolean
}
