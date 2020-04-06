package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    private lateinit var sharedPref:SharedPreferences
    var flag = true
    private var backgroundThread = Thread {
        while (flag) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        sharedPref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        secondsElapsed=sharedPref.getInt("secondsElapsed",0)
        flag = true
        backgroundThread = Thread {
        while (flag) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
            }
        }
    }
   backgroundThread.start()
  }

    override fun onPause() {
        super.onPause()
        flag = false
        val editor = sharedPref.edit()
        editor.putInt("secondsElapsed",secondsElapsed)
        editor.apply()
    }


}
