package com.sirmarty.lapodrida.domain.repository

import com.sirmarty.lapodrida.domain.entities.Game

interface GamesRepository {
    suspend fun getGamesHistory(): List<Game>
    suspend fun hasFinishedGames(): Boolean
}
