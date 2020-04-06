package org.bigawesometurtle.lab3_3

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.os.Bundle
import android.widget.Button

class Activity1 : MenuActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, Activity2::class.java).setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
    }

    override fun onResume() {
        ActivityAbout.text = "This is Activity 1"
        super.onResume()
    }

}
