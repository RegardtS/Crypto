package com.regi.crypto.model

import androidx.room.Entity
import androidx.room.PrimaryKey


class PriceResponse(
    val name: String,
    val values: List<Price>
)

@Entity
data class Price(@field:PrimaryKey val x: Long, val y: Float)