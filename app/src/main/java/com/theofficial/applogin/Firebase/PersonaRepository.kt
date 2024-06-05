package com.theofficial.applogin.Firebase

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.theofficial.applogin.Room.Dao.PersonaDao
import com.theofficial.applogin.Room.Entity.Persona
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PersonaRepository(private val personaDao: PersonaDao) {

private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("personas")


    fun addPersona(persona: Persona) {
        databaseReference.child(persona.id.toString()).setValue(persona)
            .addOnSuccessListener { Log.d("Firebase", "Data successfully written!") }
            .addOnFailureListener { e -> Log.w("Firebase", "Error writing data", e) }
    }

    fun deletePersona(personaId: String) {
        databaseReference.child(personaId).removeValue()
            .addOnSuccessListener { Log.d("Firebase", "Data successfully deleted!") }
            .addOnFailureListener { e -> Log.w("Firebase", "Error deleting data", e) }
    }

    fun updatePersona(personaId: String, updatedPersona: Persona) {
        databaseReference.child(personaId).setValue(updatedPersona)
            .addOnSuccessListener { Log.d("Firebase", "Data successfully updated!") }
            .addOnFailureListener { e -> Log.w("Firebase", "Error updating data", e) }
    }

    fun getPersonaFlow(databaseReference: DatabaseReference): Flow<List<Persona>> {
        return callbackFlow {
                val listener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val personas = snapshot.children.mapNotNull { childSnapshot ->
                            childSnapshot.getValue(Persona::class.java)
                        }
                        trySend(personas).isSuccess
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                }
                databaseReference.addValueEventListener(listener)
                awaitClose { databaseReference.removeEventListener(listener) }
        }
    }


/*
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = database.getReference("personas")

    init {
            syncData()
            listenToFirebaseChanges()
        }
    private fun listenToFirebaseChanges() {
            myRef.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                    val persona = dataSnapshot.getValue(Persona::class.java)
                    if (persona != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            personaDao.insertPersona(persona)
                        }
                    }
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                    val persona = dataSnapshot.getValue(Persona::class.java)
                    if (persona != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            personaDao.updatePersona(persona) // Asegúrate de que este método exista
                        }
                    }
                }

              override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    val persona = dataSnapshot.getValue(Persona::class.java)
                    if (persona != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            personaDao.deletePersona(persona)
                        }
                    }
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                    // No se necesita para operaciones CRUD básicas
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("Sync", "loadPost:onCancelled", databaseError.toException())
                }
            })
        }
    fun syncData() {
        GlobalScope.launch {
            val localData: LiveData<List<Persona>> = personaDao.getAll()
            localData.observeForever { personas ->
                // Obtener personas de Firebase y comparar con las locales
                myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val firebasePersonas = mutableListOf<Persona>()

                        // Obtener todas las personas de Firebase
                        for (snapshot in dataSnapshot.children) {
                            val firebasePersona = snapshot.getValue(Persona::class.java)
                            firebasePersona?.let { firebasePersonas.add(it) }
                        }

                        // Eliminar personas locales que no están en Firebase
                        personas.filterNot { persona ->
                            firebasePersonas.any { it.id == persona.id }
                        }.forEach { personaToDelete ->
                            // Llamada suspendida dentro de la coroutina
                            launch { personaDao.deletePersona(personaToDelete) }
                        }

                        // Insertar o actualizar personas locales según sea necesario
                        firebasePersonas.forEach { firebasePersona ->
                            val existingPersona = personas.find { it.id == firebasePersona.id }
                            if (existingPersona != null) {
                                // Llamada suspendida dentro de la coroutina
                                launch { personaDao.updatePersona(firebasePersona) }
                            } else {
                                // Llamada suspendida dentro de la coroutina
                                launch { personaDao.insertPersona(firebasePersona) }
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.w("Sync", "loadPost:onCancelled", databaseError.toException())
                    }
                })
            }
        }
    }
    fun syncData(origianl) {
        val localData: LiveData<List<Persona>> = personaDao.getAll()
        localData.observeForever { personas ->
            personas.forEach { persona ->
                myRef.child(persona.id.toString()).setValue(persona)
                    .addOnSuccessListener { Log.d("Sync", "Data successfully written!") }
                    .addOnFailureListener { e -> Log.w("Sync", "Error writing data", e) }
            }
        }
    }


 fun addPersona(persona: Persona) {
        myRef.child(persona.id.toString()).setValue(persona)
            .addOnSuccessListener { Log.d("Firebase", "Data successfully written!") }
            .addOnFailureListener { e -> Log.w("Firebase", "Error writing data", e) }
    }

    fun getPersonas(): LiveData<List<Persona>> {
        val personasLiveData = MutableLiveData<List<Persona>>()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val personasList = mutableListOf<Persona>()
                for (snapshot in dataSnapshot.children) {
                    val persona = snapshot.getValue(Persona::class.java)
                    if (persona != null) {
                        personasList.add(persona)
                    }
                }
                personasLiveData.value = personasList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Firebase", "loadPost:onCancelled", databaseError.toException())
            }
        })
        return personasLiveData
    }

    fun updatePersona(persona: Persona) {
        myRef.child(persona.id.toString()).setValue(persona)
            .addOnSuccessListener { Log.d("Firebase", "Data successfully updated!") }
            .addOnFailureListener { e -> Log.w("Firebase", "Error updating data", e) }
    }

    fun deletePersona(personaId: String) {
        myRef.child(personaId).removeValue()
            .addOnSuccessListener { Log.d("Firebase", "Data successfully deleted!") }
            .addOnFailureListener { e -> Log.w("Firebase", "Error deleting data", e) }
    }

 */
}