package com.rk.silvertouchapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    @ColumnInfo(name = "firstName", defaultValue = "")
    val firstName: String = "",
    @ColumnInfo(name = "lastName", defaultValue = "")
    val lastName: String = "",
    @ColumnInfo(name = "mobileNumber", defaultValue = "")
    val mobileNumber: String = "",
    @ColumnInfo(name = "emailId", defaultValue = "")
    val emailId: String = "",
    @ColumnInfo(name = "categoryId", defaultValue = "0")
    val categoryId: Int = 0,
    @ColumnInfo(name = "categoryName", defaultValue = "")
    val categoryName: String = "",
)