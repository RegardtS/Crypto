package com.regi.crypto.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.regi.crypto.database.AppDatabase
import com.regi.crypto.ui.MainViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "prices").build()
            return MainViewModel(db.priceDao()) as T
        }
        throw IllegalArgumentException("Unknown class type")
    }
}