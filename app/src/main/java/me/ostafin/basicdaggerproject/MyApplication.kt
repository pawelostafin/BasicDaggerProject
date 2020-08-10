package me.ostafin.basicdaggerproject

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import me.ostafin.basicdaggerproject.di.DaggerAppComponent
import timber.log.Timber

class MyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
    }

    private fun initializeTimber() {
        Timber.plant(Timber.DebugTree())
    }

}