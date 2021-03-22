package com.example.youtubeparser.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.youtubeparser.models.VideoInfo
import com.example.youtubeparser.data.network.PlaylistApi
import com.example.youtubeparser.data.network.RetrofitClient
import com.example.youtubeparser.models.VideoInfo2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(
    private val playlistApi: PlaylistApi
) {

    fun fetchPlaylist(
        part: String,
        channelID: String,
        key: String,
        maxValue: Int = 50
    ): MutableLiveData<VideoInfo>? {
        val data: MutableLiveData<VideoInfo> = MutableLiveData()
        playlistApi.getListFromApi(part, channelID, key,maxValue).enqueue(object : Callback<VideoInfo> {
            override fun onFailure(call: Call<VideoInfo>, t: Throwable) {
                data.value = null
            }
            override fun onResponse(call: Call<VideoInfo>, response: Response<VideoInfo>) {
                data.value = response.body()
            }
        })
        return data
    }

    fun fetchVideo(
        part: String,
        pageToken: String,
        playlistId: String,
        key: String
    ): MutableLiveData<VideoInfo>{
        val data: MutableLiveData<VideoInfo> = MutableLiveData()
        playlistApi.getItemListFormApi(part, pageToken, playlistId, key,150).enqueue(object : Callback<VideoInfo>{
            override fun onFailure(call: Call<VideoInfo>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<VideoInfo>, response: Response<VideoInfo>) {
                data.value = response.body()
            }
        })
        return data
    }
}