package org.bigawesometurtle.lab6_1

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    companion object {
        var secondsElapsed: Int = 0
    }
    var stopped=false

    class MyHandler(private val act: AppCompatActivity) : Handler() {
        override fun handleMessage(msg: Message) {
            act.findViewById<TextView>(R.id.textSecondsElapsed).text =
                    "Seconds elapsed: " + ++secondsElapsed
        }
    }

    private lateinit var sharedPref: SharedPreferences
    private lateinit var backgroundThread: Thread

    var handler: Handler = MyHandler(this)

    private val timerRunnable: Runnable = Runnable {
        while (!stopped) {
                Thread.sleep(1000)
                handler.sendEmptyMessage(0)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        backgroundThread = Thread(timerRunnable)
        backgroundThread.start()
        sharedPref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        secondsElapsed = sharedPref.getInt("secondsElapsed", 0)

    }

    override fun onPause() {
        super.onPause()
        stopped=true
        val editor = sharedPref.edit()
        editor.putInt("secondsElapsed", secondsElapsed)
        editor.apply()
    }


}
