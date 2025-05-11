package com.example.myapplication.di

import com.example.myapplication.data.api.CountryApiService
import com.example.myapplication.data.repository.CountryRepository
import com.example.myapplication.data.repository.CountryRepositoryImpl
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
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()

            // Todo: Replace with your base URL ie. "https://restcountries.com/v3.1/" or
            //  ""http://10.0.2.2:8080/" if you pointing locally
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryApiService(retrofit: Retrofit): CountryApiService {
        return retrofit.create(CountryApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCountryRepository(apiService: CountryApiService): CountryRepository {
        return CountryRepositoryImpl(apiService)
    }
} 