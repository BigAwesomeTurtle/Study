package org.bigawesometurtle.blackjack.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomEntry::class], version = 1, exportSchema = false)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun scoreDao(): RoomDao
}