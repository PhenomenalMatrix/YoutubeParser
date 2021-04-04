package com.example.youtubeparser.data.repositories

import androidx.lifecycle.MutableLiveData
import com.example.youtubeparser.models.VideoInfo
import com.example.youtubeparser.data.network.PlaylistApi
import com.example.youtubeparser.data.network.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(
    private val playlistApi: PlaylistApi
) {

    suspend fun fetchPlaylist(
        part: String,
        channelID: String,
        key: String,
        maxValue: Int = 50
    ): MutableLiveData<Resource<VideoInfo>>? {
        val data: MutableLiveData<Resource<VideoInfo>> = MutableLiveData()
        data.postValue(Resource.loading(null))
        val response = playlistApi.getListFromApi(part, channelID, key,maxValue)
        if (response.isSuccessful){
            data.postValue(Resource.success(response.body()!!))
        }else{
            data.postValue(Resource.error(null,response.errorBody().toString()))
        }
        return data
    }

   suspend fun fetchVideo(
        part: String,
        pageToken: String,
        playlistId: String,
        key: String
    ): MutableLiveData<Resource<VideoInfo>>{
        val data: MutableLiveData<Resource<VideoInfo>> = MutableLiveData()
        data.postValue(Resource.loading(null))
        val response = playlistApi.getItemListFormApi(part, pageToken, playlistId, key,150)
        if(response.isSuccessful){
            data.postValue(Resource.success(response.body()!!))
        }else{
            data.postValue(Resource.error(null,response.errorBody().toString()))
        }
        return data
    }
}