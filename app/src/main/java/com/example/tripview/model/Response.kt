package com.example.tripview.model

import com.google.gson.annotations.SerializedName

//Pexels
data class PexelsResponse(
    @SerializedName("photos") val results: List<Photo>
)

data class Photo(
    @SerializedName("id") val id: String,
    @SerializedName("alt") val description: String,
    @SerializedName("src") val urls: Urls
)

data class Urls(
    @SerializedName("landscape") val landscape: String
)


//Nominatim
data class NominatimResponse(
    @SerializedName("address") val address: Address
)

data class Address(
    @SerializedName("country") val state: String?,
    @SerializedName("city") val city: String?
)