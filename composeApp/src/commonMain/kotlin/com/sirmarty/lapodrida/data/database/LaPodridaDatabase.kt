package com.sirmarty.lapodrida.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.sirmarty.lapodrida.data.dao.GameDAO

const val DATABASE_NAME = "lapodrida.db"

@Database(entities = [GameDB::class], version = 1)
@ConstructedBy(LaPodridaConstructor::class)
@TypeConverters(PlayerConverter::class)
abstract class LaPodridaDatabase : RoomDatabase() {
    // DAO
    abstract fun getGameDAO(): GameDAO
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object LaPodridaConstructor : RoomDatabaseConstructor<LaPodridaDatabase> {
    override fun initialize(): LaPodridaDatabase
}
