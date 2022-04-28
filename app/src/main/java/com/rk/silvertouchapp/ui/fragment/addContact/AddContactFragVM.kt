package com.rk.silvertouchapp.ui.fragment.addContact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.model.Contact
import com.rk.silvertouchapp.util.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddContactFragVM(private val repository: Repository): ViewModel() {

    var tag = "AddContactFragVM"

    val categoryList: LiveData<List<Category>>
        get() = repository.getCategoryList


    fun addContact(model: Contact) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.addContact(model)
        }
    }
}