package com.example.tripview.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripview.model.NominatimApi
import com.example.tripview.model.NominatimResponse
import com.example.tripview.model.PexelsApi
import com.example.tripview.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.library.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

//private const val API_KEY = BuildConfig.API_KEY

@HiltViewModel
class PexelsViewModel @Inject constructor(
    private val pexelsApi: PexelsApi,
    private val nominatimApi: NominatimApi
) : ViewModel() {
    private val _photos = mutableStateListOf<Photo>()
    val photos: List<Photo> = _photos

    var latitude = mutableStateOf(0.0)
        private set
    var longitude = mutableStateOf(0.0)
        private set

    var prefecture = mutableStateOf("観光したい場所をタップしてください")
    var city = mutableStateOf("下に画像が表示されます")

    fun updateCoordinatesAndSearchPhotos(lat: Double, lon: Double) {
        latitude.value = lat
        longitude.value = lon

        viewModelScope.launch {
            val call: Call<NominatimResponse> = nominatimApi.reverseGeocode(
                lat = lat,
                lon = lon
            )
            call.enqueue(object : Callback<NominatimResponse> {
                override fun onResponse(call: Call<NominatimResponse>, response: Response<NominatimResponse>) {
                    if (response.isSuccessful) {
                        val nominatimResponse: NominatimResponse? = response.body()
                        if (nominatimResponse != null) {
                            val prefe: String? = nominatimResponse.address.state
                            val ct: String? = nominatimResponse.address.city
                            // prefectureを利用して何かしらの処理を行う
                            prefecture.value = prefe.toString()
                            city.value = ct.toString()
                            searchPhotos()
                        } else {
                            // レスポンスがnullの場合の処理
                            prefecture.value = "違う場所をタップしてください"
                        }
                    } else {
                        // エラーレスポンスの場合の処理
                        prefecture.value = "地名を取得できません"
                    }
                }

                override fun onFailure(call: Call<NominatimResponse>, t: Throwable) {
                    prefecture.value = "地名を取得できません: ${t.message}"
                }
            })
        }
    }

    fun searchPhotos(/*query: String*/) {
        viewModelScope.launch {
            try {
                val response = pexelsApi.searchPhotos(
                    apiKey = "r1fy3dBVSe6dh9yQNGMxGswZtRGfYaKGY4pjufv4bDWeew8NXTDrGmSD",
                    query = city.value + "  " + prefecture.value  ,
                    page = 1,
                    perPage = 10
                )
                _photos.clear()
                _photos.addAll(response.results)
            } catch (e: HttpException) {
                // Handle error
                Log.e("PexelsViewModel", "Error fetching photos", e)
            }
        }
    }
}
