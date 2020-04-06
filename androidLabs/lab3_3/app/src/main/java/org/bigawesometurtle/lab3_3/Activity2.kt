package org.bigawesometurtle.lab3_3

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class Activity2 : MenuActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        findViewById<Button>(R.id.button2).setOnClickListener {
            startActivity(Intent(this, Activity1::class.java).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            startActivity(Intent(this, Activity3::class.java).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
    }

    override fun onResume() {
        ActivityAbout.text = "This is Activity 2"
        super.onResume()
    }
}
