package com.example.myapplication.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String,
    val capital: String,
    val region: String,
    val population: Long,
    val flagUrl: String,
    val languages: List<String>,
    val currencies: List<String>
) : Parcelable 