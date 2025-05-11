package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Country

interface CountryRepository {
    suspend fun getCountries(): Result<List<Country>>
    suspend fun getCountryByName(name: String): Result<Country>
} 