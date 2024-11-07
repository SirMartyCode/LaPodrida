package com.sirmarty.lapodrida.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PlayerConverter {
    private val json = Json { encodeDefaults = true }

    @TypeConverter
    fun fromString(playersJson: String): List<PlayerDB> {
        return json.decodeFromString(playersJson)
    }

    @TypeConverter
    fun toString(players: List<PlayerDB>): String {
        return json.encodeToString(players)
    }
}