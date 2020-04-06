package org.bigawesometurtle.lab6_5.mainUI

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    val imageBitmap = MutableLiveData<Bitmap>()

    fun download(url: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val img = async(Dispatchers.IO) {
                java.net.URL(url).openStream().use {
                    return@async BitmapFactory.decodeStream(it)
                }
            }.await()

            withContext(Dispatchers.Main) {
                imageBitmap.value = img
            }
        }
    }

}