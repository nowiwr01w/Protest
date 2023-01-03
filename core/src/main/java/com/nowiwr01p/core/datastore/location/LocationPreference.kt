package com.nowiwr01p.core.datastore.location

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country
import kotlinx.serialization.Serializable

@Serializable
data class LocationPreference(
    val selectedCountry: Country = Country(),
    val selectedCity: City = City()
)