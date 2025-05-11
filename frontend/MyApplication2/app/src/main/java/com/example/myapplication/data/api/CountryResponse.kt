package com.example.myapplication.data.api

data class CountryResponse(
    val name: CountryName,
    val capital: List<String>?,
    val region: String,
    val population: Long,
    val flags: Flags,
    val languages: Map<String, String>?,
    val currencies: Map<String, Currency>?
)

data class CountryName(
    val common: String,
    val official: String
)

data class Flags(
    val png: String,
    val svg: String
)

data class Currency(
    val name: String,
    val symbol: String?
) 