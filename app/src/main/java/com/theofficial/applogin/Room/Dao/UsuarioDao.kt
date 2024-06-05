package com.theofficial.applogin.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.theofficial.applogin.Room.Entity.Usuario

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM Usuario")
    fun getAll(): LiveData<List<Usuario>>

    //funcion que devuelve un usuario o un null en caso de no ser encontrado
    @Query("SELECT * FROM Usuario WHERE usuario = :username LIMIT 1")
    fun getUserByUsername(username: String): Usuario?

    @Insert()
    suspend fun insertUser(user: Usuario)

}