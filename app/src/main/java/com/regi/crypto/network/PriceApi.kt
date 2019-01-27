package com.regi.crypto.network

import com.regi.crypto.model.PriceResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface PriceApi{
    @GET("market-price?timespan=7days&format=json")
    fun getCurrentPrice(): Observable<PriceResponse>
}