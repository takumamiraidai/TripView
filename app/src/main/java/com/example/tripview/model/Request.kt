package com.example.tripview.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PexelsApi {
    @GET("search")
    suspend fun searchPhotos(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PexelsResponse
}

interface NominatimApi {
    @GET("reverse")
    fun reverseGeocode(
        @Query("format") format: String = "json",
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("zoom") zoom: Int = 18,
        @Query("addressdetails") addressdetails: Int = 1
    ): Call<NominatimResponse>
}