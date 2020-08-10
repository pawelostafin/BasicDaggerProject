package me.ostafin.basicdaggerproject.di.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import me.ostafin.basicdaggerproject.di.qualifier.FragmentBundle


@Module
abstract class FragmentModule {

    companion object {

        @Provides
        @FragmentBundle
        fun provideFragmentBundle(fragment: Fragment): Bundle {
            return fragment.arguments!!
        }

    }

}