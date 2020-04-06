package org.bigawesometurtle.blackjack

import android.app.Application
import androidx.room.Room
import org.bigawesometurtle.blackjack.database.RoomDatabase

class App : Application() {
    lateinit var database: RoomDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, RoomDatabase::class.java, "database").build()
    }
}