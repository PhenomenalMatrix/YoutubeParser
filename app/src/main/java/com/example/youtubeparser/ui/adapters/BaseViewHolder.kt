package com.example.youtubeparser.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(position: Int)

    companion object {
        val VIEW_TYPE_EMPTY = 0
        val VIEW_TYPE_NORMAL = 1

    }
}