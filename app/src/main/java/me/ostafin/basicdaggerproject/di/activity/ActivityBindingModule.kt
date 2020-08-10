package me.ostafin.basicdaggerproject.di.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.ostafin.basicdaggerproject.di.scope.ActivityScope
import me.ostafin.basicdaggerproject.ui.main.MainActivity
import me.ostafin.basicdaggerproject.ui.main.di.MainActivityFragmentModule
import me.ostafin.basicdaggerproject.ui.main.di.MainActivityModule

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ActivityModule::class, MainActivityModule::class, MainActivityFragmentModule::class])
    abstract fun mainActivity(): MainActivity

}