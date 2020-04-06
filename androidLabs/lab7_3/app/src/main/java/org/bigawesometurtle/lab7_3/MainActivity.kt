package org.bigawesometurtle.lab7_3

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val URL_EXTRA = "url"
    val MESSAGE_TO_CLIENT = 1
    val MESSAGE_TO_SERVICE = 2
    val MESSAGE_RESPONSE = "response"
    val URL = "https://namobilu.com/u/img/ib/003/195/195003-1.jpg"

    private var serviceMessenger: Messenger? = null
    private var serviceConnected = false
    private val messenger = Messenger(ClientHandler())

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            serviceMessenger = null
            serviceConnected = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            serviceMessenger = Messenger(service)
            serviceConnected = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val message = Message.obtain(null, MESSAGE_TO_SERVICE, 0, 0).apply {
                replyTo = messenger
                data = Bundle().apply { putString(URL_EXTRA, URL) }
            }
            serviceMessenger?.send(message)
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, DownloadService::class.java)
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (serviceConnected) {
            unbindService(serviceConnection)
            serviceConnected = false
        }
    }

    inner class ClientHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_TO_CLIENT -> {
                    path_text.text = msg.data.getString(MESSAGE_RESPONSE)
                }
            }
        }
    }
}
