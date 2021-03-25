package com.example.youtubeparser.ui.adapters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtubeparser.App
import com.example.youtubeparser.base.BaseViewModel
import com.example.youtubeparser.models.VideoInfo


class PlayItemsListViewModel : BaseViewModel() {
    fun fetchPlayItems(id: String): MutableLiveData<VideoInfo>?{
        return App.repository.fetchVideo("snippet,contentDetails","",id,"AIzaSyCzVOrBBiXeVbjWoE4J_kiOwxj57LI90nA")
    }
}