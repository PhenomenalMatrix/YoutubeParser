package com.example.youtubeparser.models

import java.util.*


data class VideoInfo(val items: MutableList<Info>)
data class VideoInfo2(val items2: MutableList<InfoItems>)

data class Info(
        var id: String?=null,
        var snippet: Snippet
)

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

data class InfoItems(
        var id: String?=null,
        var snippetItems: SnippetItems
)

data class SnippetItems(
        var chanelId: String?=null,
        var title: String?=null,
        var thumbnailsItems: ThumbnailsItems?=null
)

data class ThumbnailsItems(
        var mediumItems: MediumItems?=null
)

data class MediumItems(
        var url: String?=null,
        var width: String?=null,
        var height: String?=null
)