package com.theofficial.applogin.Room.Entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Persona(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var nombres: String,
    var apellidos: String,
    var celular: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong() ?: 0,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(nombres)
        parcel.writeString(apellidos)
        parcel.writeString(celular)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Persona> {
        override fun createFromParcel(parcel: Parcel): Persona {
            return Persona(parcel)
        }

        override fun newArray(size: Int): Array<Persona?> {
            return arrayOfNulls(size)
        }
    }
}