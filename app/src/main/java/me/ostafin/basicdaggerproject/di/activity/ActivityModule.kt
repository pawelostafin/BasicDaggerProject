package me.ostafin.basicdaggerproject.di.activity

import android.app.Activity
import android.os.Bundle
import dagger.Module
import dagger.Provides
import me.ostafin.basicdaggerproject.di.qualifier.ActivityBundle

@Module
abstract class ActivityModule {

    companion object {

        @Provides
        @ActivityBundle
        fun provideActivityBundle(activity: Activity): Bundle {
            return activity.intent.extras ?: Bundle.EMPTY
        }

    }

}