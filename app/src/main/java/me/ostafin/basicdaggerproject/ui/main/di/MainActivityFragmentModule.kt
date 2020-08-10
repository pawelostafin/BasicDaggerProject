package me.ostafin.basicdaggerproject.ui.main.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.ostafin.basicdaggerproject.di.fragment.FragmentModule
import me.ostafin.basicdaggerproject.di.scope.FragmentScope
import me.ostafin.basicdaggerproject.ui.main.fragmnet.FirstFragment
import me.ostafin.basicdaggerproject.ui.main.fragmnet.di.FirstFragmentModule

@Module
abstract class MainActivityFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [FirstFragmentModule::class, FragmentModule::class])
    abstract fun firstFragment(): FirstFragment

}