package com.theofficial.applogin.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val usuario: String,
    val contraseña: String
)