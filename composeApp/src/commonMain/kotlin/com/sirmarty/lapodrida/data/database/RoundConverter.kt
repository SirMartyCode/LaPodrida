package com.sirmarty.lapodrida.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class RoundConverter {
    private val json = Json { encodeDefaults = true }

    @TypeConverter
    fun fromRoundsString(roundsJson: String): List<RoundDB> {
        return json.decodeFromString(roundsJson)
    }

    @TypeConverter
    fun toRoundsString(rounds: List<RoundDB>): String {
        return json.encodeToString(rounds)
    }
}
