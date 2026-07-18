package com.sirmarty.lapodrida

import android.app.Application
import com.sirmarty.lapodrida.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class LaPodridaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@LaPodridaApp)
        }
    }

}