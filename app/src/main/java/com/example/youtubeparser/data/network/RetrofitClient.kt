package com.example.youtubeparser.data.network

import com.example.youtubeparser.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val provideRetrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun providePlaylistAPI() = provideRetrofit.create(PlaylistApi::class.java)
}