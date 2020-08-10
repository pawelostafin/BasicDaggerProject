package me.ostafin.basicdaggerproject.ui.main.fragmnet.di

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import me.ostafin.basicdaggerproject.di.qualifier.FragmentBundle
import me.ostafin.basicdaggerproject.di.viewmodel.ViewModelKey
import me.ostafin.basicdaggerproject.ui.main.fragmnet.FirstFragment
import me.ostafin.basicdaggerproject.ui.main.fragmnet.FirstViewModel

@Module
abstract class FirstFragmentModule {

    @Binds
    abstract fun firstFragment(firstFragment: FirstFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(FirstViewModel::class)
    abstract fun firstViewModel(firstViewModel: FirstViewModel): ViewModel

    companion object {

        @Provides
        fun provideExtraInt(@FragmentBundle bundle: Bundle): Int {
            return bundle.getInt(FirstFragment.EXTRA_FRAGMENT_INT)
        }

    }

}