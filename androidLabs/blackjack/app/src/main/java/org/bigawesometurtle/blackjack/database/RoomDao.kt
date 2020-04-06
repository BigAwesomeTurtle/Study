package org.bigawesometurtle.blackjack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Методы для доступа к базе.
 */
@Dao
interface RoomDao {
    @Insert
    fun insert(roomEntry: RoomEntry)

    @Query("UPDATE score_table SET win = :win, lose = :lose WHERE playerName = :name")
    suspend fun updateScore(name: String, win: Int, lose: Int)

    @Query("SELECT * from score_table")
    suspend fun getAll(): List<RoomEntry>

    @Query("SELECT * FROM score_table WHERE playerName = :name")
    suspend fun getByName(name: String): RoomEntry?
}