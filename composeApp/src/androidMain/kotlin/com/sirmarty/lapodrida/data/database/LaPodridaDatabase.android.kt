package com.sirmarty.lapodrida.data.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

fun getDatabase(context: Context): LaPodridaDatabase {
    val dbFile = context.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<LaPodridaDatabase>(context, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}