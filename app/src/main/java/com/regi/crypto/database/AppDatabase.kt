package com.regi.crypto.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.regi.crypto.dao.PriceDao
import com.regi.crypto.model.Price

@Database(entities = [Price::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun priceDao(): PriceDao
}