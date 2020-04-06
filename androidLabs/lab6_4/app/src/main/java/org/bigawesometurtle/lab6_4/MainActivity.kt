package org.bigawesometurtle.lab6_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.bigawesometurtle.lab6_4.mainUI.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}
