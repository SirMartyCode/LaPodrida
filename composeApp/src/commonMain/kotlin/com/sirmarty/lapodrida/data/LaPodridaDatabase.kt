package com.sirmarty.lapodrida.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.sirmarty.lapodrida.data.database.GameSettingsDB

const val DATABASE_NAME = "lapodrida.db"

@Database(entities = [GameSettingsDB::class], version = 1)
@ConstructedBy(LaPodridaConstructor::class)
abstract class LaPodridaDatabase : RoomDatabase() {
    // DAO
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object LaPodridaConstructor : RoomDatabaseConstructor<LaPodridaDatabase> {
    override fun initialize(): LaPodridaDatabase
}
