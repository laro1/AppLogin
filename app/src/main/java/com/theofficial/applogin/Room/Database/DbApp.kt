package com.theofficial.applogin.Room.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theofficial.applogin.Room.Dao.PersonaDao
import com.theofficial.applogin.Room.Dao.UsuarioDao
import com.theofficial.applogin.Room.Entity.Persona
import com.theofficial.applogin.Room.Entity.Usuario

@Database(entities = [Usuario::class, Persona::class], version = 2, exportSchema = true)

abstract class DbApp : RoomDatabase() {
    abstract fun userDao(): UsuarioDao
    abstract fun personaDao(): PersonaDao
}