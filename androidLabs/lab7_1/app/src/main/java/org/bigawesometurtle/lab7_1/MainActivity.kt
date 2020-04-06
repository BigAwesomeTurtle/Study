package org.bigawesometurtle.lab7_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            startService(
                    Intent(this, DownloadService::class.java).putExtra(
                            "url",
                            "https://namobilu.com/u/img/ib/003/195/195003-1.jpg"
                    )
            )
        }
    }
}
