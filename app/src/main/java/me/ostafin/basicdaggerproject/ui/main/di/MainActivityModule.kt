package me.ostafin.basicdaggerproject.ui.main.di

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import me.ostafin.basicdaggerproject.di.qualifier.ActivityBundle
import me.ostafin.basicdaggerproject.di.scope.ActivityScope
import me.ostafin.basicdaggerproject.di.scope.ApplicationScope
import me.ostafin.basicdaggerproject.di.viewmodel.ViewModelKey
import me.ostafin.basicdaggerproject.ui.main.MainActivity
import me.ostafin.basicdaggerproject.ui.main.MainViewModel

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun mainActivity(mainActivity: MainActivity): Activity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    companion object {

        @Provides
        fun provideExtraLong(@ActivityBundle bundle: Bundle): Long {
            return bundle.getLong(MainActivity.EXTRA_LONG, 0L)
        }

    }

}