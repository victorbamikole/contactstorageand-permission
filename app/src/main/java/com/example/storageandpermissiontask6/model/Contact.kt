package com.example.storageandpermissiontask6.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(val firstName: String? = null, val lastName: String? = null, val phoneNumber: String? = null):Parcelable{

}
