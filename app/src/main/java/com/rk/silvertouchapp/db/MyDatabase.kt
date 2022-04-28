package com.rk.silvertouchapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rk.silvertouchapp.R
import com.rk.silvertouchapp.db.doa.CategoryDAO
import com.rk.silvertouchapp.db.doa.ContactDAO
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.model.Contact

@Database(
    entities =
    [
        Category::class,
        Contact::class,
    ], version = 1
)
abstract class MyDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MyDatabase::class.java,
            context.resources.getString(R.string.app_name)
        )
            .build()
    }

    abstract fun categoryDAO(): CategoryDAO
    abstract fun contactDAO(): ContactDAO
}