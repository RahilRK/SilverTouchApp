package com.rk.silvertouchapp.util

import android.app.Application
import com.rk.silvertouchapp.db.MyDatabase

class Application: Application() {

    lateinit var repository: Repository
    lateinit var globalClass: GlobalClass
    lateinit var myDatabase: MyDatabase

    override fun onCreate() {
        super.onCreate()

        init()
    }

    fun init() {

        globalClass = GlobalClass.getInstance(applicationContext)
        myDatabase = MyDatabase.getInstance(applicationContext)
        repository = Repository(globalClass,myDatabase)
    }
}