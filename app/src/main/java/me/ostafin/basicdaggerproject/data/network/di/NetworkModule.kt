package me.ostafin.basicdaggerproject.data.network.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import me.ostafin.basicdaggerproject.BuildConfig
import me.ostafin.basicdaggerproject.data.network.ApiService
import me.ostafin.basicdaggerproject.data.network.util.RxJavaErrorHandlingCallAdapterFactory
import me.ostafin.basicdaggerproject.di.scope.ApplicationScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
abstract class NetworkModule {

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"

        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().apply {

                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }

            }.build()
        }

        @Provides
        fun provideGson(): Gson {
            return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        }

        @Provides
        @ApplicationScope
        fun providerRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaErrorHandlingCallAdapterFactory.create())
                .build()
        }

        @Provides
        fun providesApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }

    }

}