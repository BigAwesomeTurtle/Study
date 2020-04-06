package org.bigawesometurtle.lab6_4.mainUI

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.net.URL

class MainViewModel : ViewModel() {
    private lateinit var asyncTask: DownloadAsyncTask

    val imgBitmap = MutableLiveData<Bitmap>()

    inner class DownloadAsyncTask :
            AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap {
            val url = urls[0]
            var bitmap: Bitmap? = null
            try {
                val inputStream = URL(url).openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap ?: Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }

        override fun onPostExecute(result: Bitmap) {
            super.onPostExecute(result)
            imgBitmap.value = result
        }
    }

    fun download(url: String) {
        asyncTask = DownloadAsyncTask()
        asyncTask.execute(url)
    }

    override fun onCleared() {
        if (::asyncTask.isInitialized)
            asyncTask.cancel(true)
        super.onCleared()
    }

}