package org.bigawesometurtle.blackjack

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_choose_cover.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class ChooseCoverActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var sharedPref: SharedPreferences
    lateinit var backs: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_cover)

        sharedPref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        backs = listOf(img_choose_back_1, img_choose_back_2, img_choose_back_3, img_choose_back_4)

        try {
            img_choose_back_4.setImageURI(Uri.fromFile(File(applicationContext.filesDir.absolutePath + "/user_back")))
        } catch (e: FileNotFoundException) {
        }

        when (sharedPref.getInt("chosenBack", 1)) {
            1 -> img_choose_back_1.setBackgroundResource(R.drawable.border)
            2 -> img_choose_back_2.setBackgroundResource(R.drawable.border)
            3 -> img_choose_back_3.setBackgroundResource(R.drawable.border)
            4 -> img_choose_back_4.setBackgroundResource(R.drawable.border)
        }

        btn_camera_back.setOnClickListener {
            dispatchTakePictureIntent()
        }

        img_choose_back_1.setOnClickListener {
            selectBack(1)
        }

        img_choose_back_2.setOnClickListener {
            selectBack(2)
        }

        img_choose_back_3.setOnClickListener {
            selectBack(3)
        }

        img_choose_back_4.setOnClickListener {
            selectBack(4)
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            saveImage(imageBitmap, "user_back")
            img_choose_back_4.setImageBitmap(imageBitmap)
        }
    }

    private fun saveImage(b: Bitmap, imageName: String) {
        val outStream: FileOutputStream
        try {
            outStream = this.applicationContext.openFileOutput(imageName, Context.MODE_PRIVATE)
            b.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun selectBack(back: Int) {
        for (i in backs.indices) {
            if (i == back - 1) backs[i].setBackgroundResource(R.drawable.border)
            else backs[i].setBackgroundResource(0)
        }
        val editor = sharedPref.edit()
        editor.putInt("chosenBack", back)
        editor.apply()
    }
}
