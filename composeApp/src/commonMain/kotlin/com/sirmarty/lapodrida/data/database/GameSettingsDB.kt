package com.sirmarty.lapodrida.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameSettingsDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val numberOfPlayers: Int,
    val isIndianRound: Boolean,
    val pointsPerWin: Int,
    val pointsPerHand: Int,
)