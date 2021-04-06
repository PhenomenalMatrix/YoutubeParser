package com.example.youtubeparser.models

import java.io.Serializable
import java.util.*


data class VideoInfo(
        var nextPageToken: String?=null,
        val items: MutableList<Info>
)


data class Info(
        var id: String?=null,
        var snippet: Snippet,
        var contentDetails: ContentDetails?=null
):Serializable

data class  ContentDetails(
        var itemCount:Int?=null,
        var videoId:String?=null
):Serializable

data class Snippet(
        var channelId: String?=null,
        var title: String?=null,
        var description: String?=null,
        var thumbnails: Thumbnails?=null,
        var channelTitle: String?=null,
        var publishedAt:  Date?=null
)

data class Thumbnails(
        var medium: Medium?=null
)

data class Medium(
        var url: String?=null,
        var width: String?=null,
        var height: String?=null
)


