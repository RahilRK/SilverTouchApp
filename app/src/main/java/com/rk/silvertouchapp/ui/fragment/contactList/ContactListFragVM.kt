package com.rk.silvertouchapp.ui.fragment.contactList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.model.Contact
import com.rk.silvertouchapp.util.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListFragVM(private val repository: Repository): ViewModel() {

    var tag = "ContactListFragVM"

    val contactList: LiveData<List<Contact>>
        get() = repository.getContactList

    fun deleteContact(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteContact(id)
        }
    }
}