package org.bigawesometurtle.blackjack.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_table")
data class RoomEntry(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var playerName: String = "",
        var win: Int = 0,
        var lose: Int = 0
)