package com.example.storageandpermissiontask6.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contacts(var id: String? = null, var firstName: String? = null,var lastName: String? = null, var phoneNumber: String? = null): Parcelable


