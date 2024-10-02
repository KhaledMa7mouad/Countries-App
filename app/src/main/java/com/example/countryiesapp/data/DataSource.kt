package com.example.countryiesapp.data

import com.example.countryiesapp.R
import com.example.countryiesapp.model.Country

class DataSource {

    fun getCountries() = listOf(
        Country(R.string.egypt, R.drawable.egyptian_flag, 29.9774614,31.1329645),
        Country(R.string.madagascar, R.drawable.madagascar_flag, 18.7669, 46.8691),
        Country(R.string.palastina, R.drawable.palestinian_flag, 31.9522, 35.2332),
        Country(R.string.russia, R.drawable.russian_flag, 61.5240, 105.3188),

    )
}


