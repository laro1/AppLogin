package com.theofficial.applogin.Models.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theofficial.applogin.Room.Database.DbAppIni.Companion.db
import com.theofficial.applogin.Room.Entity.Usuario

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroViewModel : ViewModel() {
    fun registrarUsuario(username: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                db.userDao().insertUser(Usuario(0, username, password))
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError()
                }
            }
        }
    }
}