package org.BigAwesomeTurtle.bar_db_app.model

import android.util.Log
import java.security.MessageDigest

    fun String.sha512_5(): String {
        var res=this
        for (i in 0..4){
            res=hashString(res, "SHA-512")
        }
        return res
    }

    private fun hashString(input: String, algorithm: String): String {
        return MessageDigest
            .getInstance(algorithm)
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }
