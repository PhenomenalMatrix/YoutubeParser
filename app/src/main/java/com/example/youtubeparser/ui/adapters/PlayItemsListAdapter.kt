package com.example.youtubeparser.ui.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeparser.R
import com.example.youtubeparser.extensions.loadImage
import com.example.youtubeparser.models.Info
import kotlinx.android.synthetic.main.item_playlist.view.*

class PlayItemsListAdapter(var listener: Listener): RecyclerView.Adapter<BaseViewHolder>(){

    var list = mutableListOf<Info>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return PlayItemsListViewHolder(view)
    }

    fun addItems(list: MutableList<Info>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class PlayItemsListViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun onBind(position: Int) {
            itemView.apply {
                plashka_view.isVisible = false
                plashka_tv.isVisible = false
                title.text = list[position].snippet.title
                subtitle.text = list[position].snippet.publishedAt.toString()
                list[position].snippet.thumbnails?.medium?.url?.let { icon.loadImage(it)
                setOnClickListener {
                 listener.onItemClicked(list[position].contentDetails?.videoId?:"", list[position].snippet.title?:"",list[position].snippet.description?:"")
                }
                }
            }

        }

    }

    interface Listener{
        fun onItemClicked(id: String, title: String, desc: String)
    }

}