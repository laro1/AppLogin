package com.theofficial.applogin.Models.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.theofficial.applogin.Firebase.PersonaRepository
import com.theofficial.applogin.Room.Dao.PersonaDao
import com.theofficial.applogin.Room.Database.DbApp
import com.theofficial.applogin.Room.Database.DbAppIni.Companion.db
import com.theofficial.applogin.Room.Entity.Persona
class MainViewModel(application: Application) : AndroidViewModel(application) {

    val personas: LiveData<List<Persona>> = db.personaDao().getAll()

}
