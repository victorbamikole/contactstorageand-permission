package com.example.storageandpermissiontask6.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

//Contacts Data class
@Parcelize
//@get:Exclude
data class Contacts(var id: String? = null, var firstName: String? = null, var lastName: String? = null, var phoneNumber: String? = null): Parcelable


