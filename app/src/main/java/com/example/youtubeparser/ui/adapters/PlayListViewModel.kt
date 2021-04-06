package com.example.youtubeparser.ui.adapters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeparser.models.VideoInfo
import com.example.youtubeparser.App
import com.example.youtubeparser.base.BaseViewModel
import com.example.youtubeparser.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayListViewModel : BaseViewModel() {


    suspend fun fetchPlaylist(pageToken: String) : MutableLiveData<Resource<VideoInfo>>? {
        return  App.repository.fetchPlaylist("snippet,contentDetails", "UC2Ru64PHqW4FxoP0xhQRvJg" ,"AIzaSyCzVOrBBiXeVbjWoE4J_kiOwxj57LI90nA",pageToken)
    }

}