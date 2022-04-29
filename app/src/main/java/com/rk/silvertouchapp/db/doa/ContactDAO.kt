package com.rk.silvertouchapp.db.doa

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.model.Contact

@Dao
interface ContactDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContact(model: Contact)

    @Query("select * from contact")
    fun getContactList() :LiveData<List<Contact>>

    @Query("DELETE FROM contact WHERE id = :id")
    suspend fun deleteContact(id : Int)

    @Query("select * from contact where " +
            "firstName LIKE '%' ||:keyword|| '%' OR " +
            "mobileNumber LIKE '%' ||:keyword|| '%'")
    fun searchContactList(keyword: String) :LiveData<List<Contact>>

    @Query("select * from contact where categoryName = :categoryName")
    fun filterContactList(categoryName: String) :LiveData<List<Contact>>
}