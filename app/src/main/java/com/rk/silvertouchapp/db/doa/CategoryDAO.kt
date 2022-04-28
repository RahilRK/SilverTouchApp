package com.rk.silvertouchapp.db.doa

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rk.silvertouchapp.model.Category

@Dao
interface CategoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(model: Category)

    @Query("select * from category")
    fun getCategoryList() :LiveData<List<Category>>

    @Query("DELETE FROM category WHERE id = :id")
    suspend fun deleteCategory(id : Int)
}