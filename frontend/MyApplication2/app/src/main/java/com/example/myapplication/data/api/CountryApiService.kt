package com.example.myapplication.data.api

import com.example.myapplication.data.api.CountryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApiService {
    @GET("all")
    suspend fun getCountries(): List<CountryResponse>

    @GET("name/{name}")
    suspend fun getCountryByName(@Path("name") name: String): List<CountryResponse>
}