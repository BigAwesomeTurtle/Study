package com.lyubchenkova.cosmetics_shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoginActivity : AppCompatActivity() {
    var cart = hashMapOf<Int,Int>()
    var token:String?=null
    var login:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        if(intent.extras?.getSerializable("HashMap")!=null){
            cart= (intent.extras?.getSerializable("HashMap") as HashMap<Int, Int>)
        }
    }
}
