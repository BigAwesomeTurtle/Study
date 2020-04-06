package org.bigawesometurtle.lab3_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class Activity1 : MenuActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)
        findViewById<Button>(R.id.btn_from_first_to_second).setOnClickListener {
            startActivity(Intent(this, Activity2::class.java))
        }
    }

    override fun onResume() {
        ActivityAbout.text = "This is Activity 1"
        super.onResume()
    }

}
