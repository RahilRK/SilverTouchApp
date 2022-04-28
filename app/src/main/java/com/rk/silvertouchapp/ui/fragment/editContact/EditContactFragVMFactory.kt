package com.rk.silvertouchapp.ui.fragment.editContact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rk.silvertouchapp.util.Repository

class EditContactFragVMFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditContactFragVM(repository) as T
    }
}