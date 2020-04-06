package org.bigawesometurtle.lab6_2

import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object {
        var secondsElapsed: Int = 0
    }

    private lateinit var sharedPref: SharedPreferences
    private lateinit var timerAsyncTask: TimerAsyncTask

    private class TimerAsyncTask internal constructor(private val act: AppCompatActivity) :
            AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void?): Void? {
            while (!isCancelled) {
                TimeUnit.SECONDS.sleep(1)
                publishProgress()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
            act.findViewById<TextView>(R.id.textSecondsElapsed).text =
                    "Seconds elapsed: " + ++secondsElapsed

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()
        timerAsyncTask = TimerAsyncTask(this)
        timerAsyncTask.execute()
        sharedPref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        secondsElapsed = sharedPref.getInt("secondsElapsed", 0)
    }

    override fun onPause() {
        super.onPause()
        timerAsyncTask.cancel(true)
        val editor = sharedPref.edit()
        editor.putInt("secondsElapsed", secondsElapsed)
        editor.apply()
    }
}
