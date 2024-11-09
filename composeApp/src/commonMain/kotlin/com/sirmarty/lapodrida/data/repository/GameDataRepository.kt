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
        database.getGameDAO().createGame(GameDB.fromDomain(game))
    }

    override suspend fun isThereUnfinishedGame(): Boolean {
        return database.getGameDAO().isThereUnfinishedGame()
    }

    override suspend fun deleteCurrentGame() {
        database.getGameDAO().deleteCurrentGame()
    }

    override suspend fun getFinishedGames(): List<Game> {
        return database.getGameDAO().getFinishedGames().map { it.toDomain() }
    }
}