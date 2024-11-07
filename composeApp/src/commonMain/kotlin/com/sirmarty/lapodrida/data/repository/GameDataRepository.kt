package com.sirmarty.lapodrida.data.repository

import com.sirmarty.lapodrida.data.database.GameDB
import com.sirmarty.lapodrida.data.database.LaPodridaDatabase
import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.repository.GameRepository

class GameDataRepository(val database: LaPodridaDatabase) : GameRepository {

    override suspend fun getCurrentGame(): Game? {
        return database.getGameDAO().getCurrentGame()?.toDomain()
    }

    override suspend fun createGame(game: Game) {
        return database.getGameDAO().createGame(GameDB.fromDomain(game))
    }
}