package org.bigawesometurtle.lab3_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.lang.StringBuilder

class ActivityAbout : AppCompatActivity() {

    companion object {
        var text = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val sb = StringBuilder()
        findViewById<TextView>(R.id.textView).text = text
    }
}
