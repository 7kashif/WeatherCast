package com.kashif.weathercast.di

import com.kashif.weathercast.Constants
import com.kashif.weathercast.api.WeatherApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = Constants.BASE_URL

//    @Provides
//    @Singleton
//    fun provideMoshi(): Moshi =
//        Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofitInstance(baseUrl: String): WeatherApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)

}