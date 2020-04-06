package org.bigawesometurtle.lab6_3

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    companion object {
        var secondsElapsed: Int = 0
    }

    private val viewScope = CoroutineScope(Dispatchers.Main)
    private lateinit var sharedPref: SharedPreferences
    private lateinit var timer: Job


    private fun startCoroutineTimer(repeatMillis: Long = 0, action: () -> Unit) =
            viewScope.launch {
                while (true) {
                    delay(repeatMillis)
                    action()
                }
            }

    private fun getContinueTimer() = startCoroutineTimer(1000) {
        this.findViewById<TextView>(R.id.textSecondsElapsed).text = "Seconds elapsed: " + ++secondsElapsed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()
        timer = getContinueTimer()
        sharedPref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        secondsElapsed = sharedPref.getInt("secondsElapsed", 0)
    }

    override fun onPause() {
        super.onPause()
        viewScope.cancel()
        val editor = sharedPref.edit()
        editor.putInt("secondsElapsed", secondsElapsed)
        editor.apply()
    }
}
