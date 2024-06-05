package com.theofficial.applogin.Models.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theofficial.applogin.Room.Database.DbAppIni.Companion.db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess
    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val credentialsValid = withContext(Dispatchers.IO) {
                    validarCredenciales(username, password)
                }
                _loginSuccess.value = credentialsValid
            } catch (e: Exception) {
                _loginSuccess.value = false
            }
        }
    }

    private suspend fun validarCredenciales(username: String, password: String): Boolean {
        val user = db.userDao().getUserByUsername(username)
        return user != null && user.contraseña == password
    }

}