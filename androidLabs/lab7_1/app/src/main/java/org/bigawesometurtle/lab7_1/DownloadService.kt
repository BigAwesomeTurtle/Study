package org.bigawesometurtle.lab7_1

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.FileOutputStream
import java.net.URL

class DownloadService : IntentService("DownloadService") {

    val URL_EXTRA = "url"
    val IMG_TITLE = "I_love_android"
    val BROADCAST_TAG = "load_url_message"
    val MESSAGE_EXTRA = "path_extra"

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
}