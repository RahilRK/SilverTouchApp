package com.rk.silvertouchapp.util

import androidx.lifecycle.LiveData
import com.rk.silvertouchapp.db.MyDatabase
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.model.Contact

class Repository(private val globalClass: GlobalClass,
                 private val myDatabase: MyDatabase
) {

    var tag = "Repository"

    suspend fun addCategory(model: Category) {

        myDatabase.categoryDAO().addCategory(model)
    }

    val getCategoryList: LiveData<List<Category>>
        get() = myDatabase.categoryDAO().getCategoryList()

    suspend fun deleteCategory(id: Int) {

        myDatabase.categoryDAO().deleteCategory(id)
    }

    suspend fun addContact(model: Contact) {

        myDatabase.contactDAO().addContact(model)
    }

    val getContactList: LiveData<List<Contact>>
        get() = myDatabase.contactDAO().getContactList()

    suspend fun deleteContact(id: Int) {

        myDatabase.contactDAO().deleteContact(id)
    }
}