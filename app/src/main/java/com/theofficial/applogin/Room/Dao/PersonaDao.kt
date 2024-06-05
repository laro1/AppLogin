package com.theofficial.applogin.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.theofficial.applogin.Room.Entity.Persona


@Dao
interface PersonaDao {

    @Query("SELECT * FROM Persona")
    fun getAll(): LiveData<List<Persona>>


    @Insert()
    suspend fun insertPersona(persona: Persona):Long

    @Update()
    suspend fun updatePersona(persona: Persona):Int

    @Delete()
    suspend fun deletePersona(persona: Persona):Int

}