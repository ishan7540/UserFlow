package com.example.usermanagementsystem.dev.core.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val noteTitle: String,
    val noteDesc: String,
    val timestamp: Long = System.currentTimeMillis(),
    val userName: String,
    val email: String
) : Parcelable

