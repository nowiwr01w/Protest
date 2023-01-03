package com.nowiwr01p.domain.location.api

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country

class LocationApi(
    private val context: Context,
    private val gson: Gson
) {

    private fun AssetManager.readAssetsFile(fileName: String) = open(fileName)
        .bufferedReader()
        .use { reader -> reader.readText() }

    /**
     * COUNTRIES
     */
    fun getCountries() = gson
        .fromJson(context.assets.readAssetsFile(COUNTRIES), CountriesWrap::class.java)
        .list

    data class CountriesWrap(
        val list: List<Country>
    )

    /**
     * CITIES
     */
    fun getCities() = gson
        .fromJson(context.assets.readAssetsFile(CITIES), CitiesWrap::class.java)
        .list

    data class CitiesWrap(
        val list: List<City>
    )

    private companion object {
        const val CITIES = "russia_cities.json"
        const val COUNTRIES = "countries.json"
    }
}