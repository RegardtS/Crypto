package com.regi.crypto.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.regi.crypto.model.Price

@Dao
interface PriceDao{
    @get:Query("SELECT * FROM price")
    val prices: List<Price>

    @Insert
    fun insertAll(vararg prices: Price)
}