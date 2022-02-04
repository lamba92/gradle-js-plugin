package org.jetbrains.gradle

import crypto.createHash

class Security {

    val sha512
        get() = createHash("sha512")
    val sha256
        get() = createHash("sha256")
    val sha1
        get() = createHash("sha1")
    val md5
        get() = createHash("md5")

}