package org.bigawesometurtle.lab7_3

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.*
import android.util.Log
import java.io.FileOutputStream
import java.net.URL

class DownloadService : IntentService("DownloadService") {

    val URL_EXTRA = "url"
    val IMG_TITLE = "I_love_android"
    val BROADCAST_TAG = "load_url_message"
    val MESSAGE_EXTRA = "path_extra"
    val MESSAGE_RESPONSE = "response"
    val MESSAGE_TO_CLIENT = 1
    val MESSAGE_TO_SERVICE = 2

    private lateinit var messenger: Messenger

    override fun onBind(intent: Intent): IBinder? {
        messenger = Messenger(@SuppressLint("HandlerLeak") object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    MESSAGE_TO_SERVICE -> {
                        DownloadAsyncTask(msg.replyTo).execute(msg.data.getString(URL_EXTRA))
                    }
                }
            }
        })
        return messenger.binder
    }

    override fun onHandleIntent(intent: Intent?) {
        val url = intent?.getStringExtra(URL_EXTRA)
        if (url == null)
            sendBroadcast("null url")
        else {
            download(url)
        }
    }

    private fun download(url: String): String {
        val bitmap = URL(url).openStream().use {
            return@use BitmapFactory.decodeStream(it)
        }
        val path = saveImage(bitmap, IMG_TITLE)
        sendBroadcast(path)
        return path
    }

    private fun saveImage(b: Bitmap, imageName: String): String {
        val outStream: FileOutputStream
        try {
            outStream = this.applicationContext.openFileOutput(imageName, Context.MODE_PRIVATE)
            b.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.close()
        } catch (e: Exception) {
            Log.d("saveImage", " Error with saving!")
            e.printStackTrace()
        }
        return applicationContext.getFileStreamPath(imageName).absolutePath
    }

    private fun sendBroadcast(msg: String) {
        sendBroadcast(Intent(BROADCAST_TAG).putExtra(MESSAGE_EXTRA, msg))
    }

    inner class DownloadAsyncTask(private val receiver: Messenger? = null) :
            AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg urls: String): String? {
            val url = urls[0]
            var str: String? = null
            try {
                str = download(url)
            } catch (e: Exception) {
                Log.e("Error loading", e.message)
            }
            return str
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            val message = Message.obtain(null, MESSAGE_TO_CLIENT, 0, 0).apply {
                data = Bundle().apply { putString(MESSAGE_RESPONSE, result) }
            }
            receiver?.send(message)
        }
    }
}
