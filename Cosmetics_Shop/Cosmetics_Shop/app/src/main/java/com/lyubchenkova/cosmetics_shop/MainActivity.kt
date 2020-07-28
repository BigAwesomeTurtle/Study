package com.lyubchenkova.cosmetics_shop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var cart = hashMapOf<Int,Int>()
    var token:String?=null
    var login:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        token=intent.extras?.getString("token")
        login=intent.extras?.getString("login")

        if(intent.extras?.getSerializable("HashMap")!=null){
            cart= (intent.extras?.getSerializable("HashMap") as HashMap<Int, Int>)
        }
    }

}
