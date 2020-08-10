package me.ostafin.basicdaggerproject.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.ostafin.basicdaggerproject.MyApplication
import me.ostafin.basicdaggerproject.data.network.di.NetworkModule
import me.ostafin.basicdaggerproject.di.activity.ActivityBindingModule
import me.ostafin.basicdaggerproject.di.module.AppModule
import me.ostafin.basicdaggerproject.di.scope.ApplicationScope

@ApplicationScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        NetworkModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<MyApplication>

}