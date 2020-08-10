package me.ostafin.basicdaggerproject.di.module

import android.content.Context
import dagger.Binds
import dagger.Module
import me.ostafin.basicdaggerproject.MyApplication
import me.ostafin.basicdaggerproject.di.qualifier.ApplicationContext

@Module
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun bindApplicationContext(application: MyApplication): Context

    companion object {

        //TODO app provides functions

    }

}