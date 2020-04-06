package org.bigawesometurtle.lab3_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

open class MenuActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.about, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                startActivity(Intent(this, ActivityAbout::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
