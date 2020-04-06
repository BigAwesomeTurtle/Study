package org.bigawesometurtle.lab3_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ActivityAbout : AppCompatActivity() {

    companion object {
        var text = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        findViewById<TextView>(R.id.textView).text = text
    }
}
