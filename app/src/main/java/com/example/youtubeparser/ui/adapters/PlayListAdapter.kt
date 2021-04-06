package com.example.youtubeparser.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubeparser.R
import com.example.youtubeparser.extensions.loadImage
import com.example.youtubeparser.models.Info
import com.example.youtubeparser.ui.activity.PlayListActivity
import kotlinx.android.synthetic.main.item_playlist.view.*

class PlayListAdapter(
    val onItemClick: (Info) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    var list = mutableListOf<Info>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return PlayListViewHolder(view)
    }

    fun addItems(list: MutableList<Info>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)

        holder.itemView.setOnClickListener {
            onItemClick(list[position])
        }
    }

    inner class PlayListViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun onBind(position: Int) {
            itemView.apply {
                title.text = list[position].snippet.title
                subtitle.text = list[position].snippet.description
                list[position].snippet.thumbnails?.medium?.url?.let { icon.loadImage(it) }
            }
        }
    }
}