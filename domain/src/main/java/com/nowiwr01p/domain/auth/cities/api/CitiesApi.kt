package com.nowiwr01p.domain.auth.cities.api

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.nowiwr01p.core.datastore.cities.data.City

class CitiesApi(
    private val context: Context,
    private val gson: Gson
) {

    private fun AssetManager.readAssetsFile(fileName: String) = open(fileName)
        .bufferedReader()
        .use { reader -> reader.readText() }

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
    }
}