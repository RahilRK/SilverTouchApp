package com.rk.silvertouchapp.ui.fragment.addCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.util.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryFragVM(private val repository: Repository): ViewModel() {

    var tag = "CategoryFragVM"

    val categoryList: LiveData<List<Category>>
        get() = repository.getCategoryList


    fun addCategory(model: Category) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.addCategory(model)
        }
    }

    fun deleteCategory(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCategory(id)
        }
    }
}