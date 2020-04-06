package org.bigawesometurtle.lab7_2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var broadcastReceiver: BroadcastReceiver
    val MESSAGE_EXTRA = "path_extra"
    val BROADCAST_TAG = "load_url_message"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val msg = intent?.getStringExtra(MESSAGE_EXTRA)
                path_text.text = msg
            }
        }

        registerReceiver(broadcastReceiver, IntentFilter(BROADCAST_TAG))

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}
