package com.example.myapplication.data.repository

import com.example.myapplication.data.api.CountryApiService
import com.example.myapplication.data.model.Country
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val apiService: CountryApiService
) : CountryRepository {

    override suspend fun getCountries(): Result<List<Country>> = runCatching {
        apiService.getCountries().map { countryResponse ->
            Country(
                name = countryResponse.name.common,
                capital = countryResponse.capital?.firstOrNull() ?: "N/A",
                region = countryResponse.region,
                population = countryResponse.population,
                flagUrl = countryResponse.flags.png,
                languages = countryResponse.languages?.values?.toList() ?: emptyList(),
                currencies = countryResponse.currencies?.values?.map { it.name } ?: emptyList()
            )
        }
    }

    override suspend fun getCountryByName(name: String): Result<Country> = runCatching {
        val countryResponse = apiService.getCountryByName(name).first()
        Country(
            name = countryResponse.name.common,
            capital = countryResponse.capital?.firstOrNull() ?: "N/A",
            region = countryResponse.region,
            population = countryResponse.population,
            flagUrl = countryResponse.flags.png,
            languages = countryResponse.languages?.values?.toList() ?: emptyList(),
            currencies = countryResponse.currencies?.values?.map { it.name } ?: emptyList()
        )
    }
}