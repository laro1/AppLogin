package com.theofficial.applogin.Models.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theofficial.applogin.Room.Database.DbAppIni.Companion.db
import com.theofficial.applogin.Room.Entity.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrarPersonaViewModel : ViewModel() {

    fun registrarPersona(persona: Persona, onSuccess: () -> Unit, onError: () -> Unit) {

        viewModelScope.launch {
            try {
                db.personaDao().insertPersona(persona)
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

    fun actualizarPersona(persona: Persona, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                db.personaDao().updatePersona(persona)
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

    fun eliminarPersona(persona: Persona, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                db.personaDao().deletePersona(persona)
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