package com.lyubchenkova.cosmetics_shop.Utils

import java.security.MessageDigest

fun String.CosmShopHash(login:String): String {
    var res=this
    res = "%s%s%s".format(login,res,login)
    for (i in 0..9){
        res=hashString(res, "SHA-1")
    }
    res = hashString(res, "SHA-512")
    return res
}

private fun hashString(input: String, algorithm: String): String {
    return MessageDigest
        .getInstance(algorithm)
        .digest(input.toByteArray())
        .fold("", { str, it -> str + "%02x".format(it) })
}