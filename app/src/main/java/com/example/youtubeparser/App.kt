package com.example.youtubeparser

import android.app.Application
import com.example.youtubeparser.data.network.PlaylistApi
import com.example.youtubeparser.data.network.RetrofitClient
import com.example.youtubeparser.data.repositories.Repository

class App : Application() {

    companion object {
        lateinit var repository: Repository
        lateinit var playlistService: PlaylistApi
    }

    override fun onCreate() {
        super.onCreate()
        playlistService = RetrofitClient().providePlaylistAPI()
        repository = Repository(
            playlistService
        )
    }
}