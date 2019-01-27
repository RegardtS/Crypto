package com.regi.crypto.base

import androidx.lifecycle.ViewModel
import com.regi.crypto.injection.component.DaggerViewModelInjector
import com.regi.crypto.injection.component.ViewModelInjector
import com.regi.crypto.injection.module.NetworkModule
import com.regi.crypto.ui.MainViewModel

abstract class BaseViewModel:ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject(){
        when(this){
            is MainViewModel -> injector.inject(this)
        }
    }
}