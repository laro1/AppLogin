package com.theofficial.applogin.Room.Database

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DbAppIni : Application() {
    companion object {
        lateinit var db: DbApp
    }

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            db = Room.databaseBuilder(
                applicationContext,
                DbApp::class.java,
                "database01"
            ).fallbackToDestructiveMigration().build()
        }

    }
}