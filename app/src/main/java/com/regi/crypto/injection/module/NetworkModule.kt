package com.regi.crypto.injection.module

import com.regi.crypto.network.PriceApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun providePriceApi(retrofit: Retrofit):PriceApi{
        return retrofit.create(PriceApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit{
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.blockchain.info/charts/")
            .build()
    }
}