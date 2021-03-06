package org.bigawesometurtle.lab3_3

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class Activity3 : MenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)
        findViewById<Button>(R.id.button4).setOnClickListener {
            startActivity(Intent(this, Activity1::class.java).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            startActivity(Intent(this, Activity2::class.java).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
    }

    override fun onResume() {
        ActivityAbout.text = "This is Activity 3"
        super.onResume()
    }
}
