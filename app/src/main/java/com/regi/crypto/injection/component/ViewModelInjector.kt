package com.regi.crypto.injection.component

import com.regi.crypto.injection.module.NetworkModule
import com.regi.crypto.ui.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector{

    fun inject(mainViewModel: MainViewModel)

    @Component.Builder
    interface Builder{
        fun build(): ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }

}