package org.bigawesometurtle.lab3_2

import android.os.Bundle
import android.widget.Button

class Activity3 : MenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val RETURN_TO_FIRST = 1
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)
        findViewById<Button>(R.id.btn_from_third_to_first).setOnClickListener {
            setResult(RETURN_TO_FIRST)
            this.finish()
        }
        findViewById<Button>(R.id.btn_from_third_to_second).setOnClickListener { this.finish() }
    }

    override fun onResume() {
        ActivityAbout.text = "This is Activity 3"
        super.onResume()
    }
}
