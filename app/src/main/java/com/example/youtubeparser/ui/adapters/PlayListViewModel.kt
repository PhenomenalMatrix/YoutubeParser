package com.example.youtubeparser.ui.adapters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtubeparser.models.VideoInfo
import com.example.youtubeparser.App
import com.example.youtubeparser.base.BaseViewModel

class PlayListViewModel : BaseViewModel() {

    fun fetchPlaylist(): MutableLiveData<VideoInfo>? {
        return App.repository.fetchPlaylist("snippet,contentDetails", "UC2Ru64PHqW4FxoP0xhQRvJg" ,"AIzaSyCzVOrBBiXeVbjWoE4J_kiOwxj57LI90nA")
    }

}