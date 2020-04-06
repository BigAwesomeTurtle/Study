package org.bigawesometurtle.lab3_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button


class Activity2 : MenuActivity() {
    val FIRST_OR_SECOND = 0
    val RETURN_TO_FIRST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        findViewById<Button>(R.id.btn_from_second_to_first).setOnClickListener {
            this.finish()
        }
        findViewById<Button>(R.id.btn_from_second_to_third).setOnClickListener {
            startActivityForResult(Intent(this, Activity3::class.java), FIRST_OR_SECOND)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RETURN_TO_FIRST) this.finish()
    }

    override fun onResume() {
        ActivityAbout.text = "This is Activity 2"
        super.onResume()
    }
}
