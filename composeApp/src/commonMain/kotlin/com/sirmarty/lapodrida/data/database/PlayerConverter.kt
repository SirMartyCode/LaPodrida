package com.sirmarty.lapodrida.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class PlayerConverter {
    private val json = Json { encodeDefaults = true }

    @TypeConverter
    fun fromPlayersString(playersJson: String): List<PlayerDB> {
        return json.decodeFromString(playersJson)
    }

    @TypeConverter
    fun toPlayersString(players: List<PlayerDB>): String {
        return json.encodeToString(players)
    }
}