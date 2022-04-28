package com.rk.silvertouchapp.ui.fragment.editContact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.model.Contact
import com.rk.silvertouchapp.util.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditContactFragVM(private val repository: Repository): ViewModel() {

    var tag = "EditContactFragVM"

    fun editContact(model: Contact) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.addContact(model)
        }
    }
}