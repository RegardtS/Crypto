package com.regi.crypto.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.regi.crypto.base.BaseViewModel
import com.regi.crypto.dao.PriceDao
import com.regi.crypto.model.Price
import com.regi.crypto.model.PriceResponse
import com.regi.crypto.network.PriceApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel(private val priceDao: PriceDao) : BaseViewModel(){

    @Inject
    lateinit var priceApi: PriceApi
    private lateinit var subscription: Disposable

    private val errorString = MutableLiveData<String>()
    private val priceList = MutableLiveData<List<Price>>()

    init{
        loadItems()
    }

    fun loadItems(){
        subscription = Observable.fromCallable { priceDao.prices }
            .concatMap {
                    dbPriceList ->
                if(dbPriceList.isEmpty())
                    priceApi.getCurrentPrice().concatMap {
                            dbPriceList:PriceResponse -> priceDao.insertAll(*dbPriceList.values.toTypedArray())
                        Observable.just(dbPriceList)
                    }
                else
                    Observable.just(dbPriceList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrievePostListSuccess(result)},
                { onRetrievePostListError() }
            )
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onRetrievePostListSuccess(result: Any?){
        when (result) {
            is PriceResponse -> priceList.value = result.values
            is List<Any?> -> priceList.value = result as List<Price>
        }
    }

    private fun onRetrievePostListError(){
        errorString.value = "An error appeared!"
    }

    fun priceList(): LiveData<List<Price>> = priceList
    fun errorOccurred(): LiveData<String> = errorString
}