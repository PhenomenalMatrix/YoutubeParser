package com.example.youtubeparser.data.network

import com.example.youtubeparser.models.VideoInfo
import com.example.youtubeparser.models.VideoInfo2
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaylistApi {
    @GET("youtube/v3/playlists")
    suspend fun getListFromApi(
        @Query("part")part: String,
        @Query("channelId")channelId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResult: Int
    ): Response<VideoInfo>

    @GET("youtube/v3/playlistItems")
    fun getItemListFormApi(
        @Query("part") part: String,
        @Query("pageToken") pageToken: String,
        @Query("playlistId") playlistId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResult: Int
    ): Call<VideoInfo>
}