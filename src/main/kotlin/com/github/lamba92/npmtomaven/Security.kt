package com.github.lamba92.npmtomaven

import org.apache.commons.codec.digest.DigestUtils
import java.security.MessageDigest

class Security {

    val sha512 = DigestUtils(MessageDigest.getInstance("SHA-512"))
    val sha256 = DigestUtils(MessageDigest.getInstance("SHA-256"))
    val sha1 = DigestUtils(MessageDigest.getInstance("SHA-1"))
    val md5 = DigestUtils(MessageDigest.getInstance("MD5"))

}