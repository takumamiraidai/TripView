package com.example.tripview.di

import com.example.tripview.model.NominatimApi
import com.example.tripview.model.PexelsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PexelsModule {

    @Provides
    @Singleton
    @Named("one")
    fun provideRetrofitForPexels(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun providePexelsApiService(@Named("one")retrofit: Retrofit): PexelsApi = retrofit.create(PexelsApi::class.java)


}

@Module
@InstallIn(SingletonComponent::class)
object NominatimModule {
    @Provides
    @Singleton
    @Named("two")
    fun provideRetrofitForNominatim(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideNominatimService(@Named("two")retrofit: Retrofit): NominatimApi = retrofit.create(NominatimApi::class.java)
}


