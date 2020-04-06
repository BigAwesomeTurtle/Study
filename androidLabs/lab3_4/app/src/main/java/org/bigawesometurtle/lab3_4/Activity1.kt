package org.bigawesometurtle.lab3_4

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.widget.Button

class Activity1 : MenuActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)
        findViewById<Button>(R.id.from_first_to_second).setOnClickListener {
            startActivity(Intent(this, Activity2::class.java).setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<Button>(R.id.to_second_with_no_history).setOnClickListener {
            startActivity(Intent(this, Activity2::class.java).setFlags(FLAG_ACTIVITY_NO_HISTORY))
        }
    }

    override fun onResume() {
        ActivityAbout.text = "This is Activity 1"
        super.onResume()
    }

}
