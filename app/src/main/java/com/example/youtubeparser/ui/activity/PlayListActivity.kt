package com.example.youtubeparser.ui.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeparser.R
import com.example.youtubeparser.base.BaseActivity
import com.example.youtubeparser.extensions.showToastLong
import com.example.youtubeparser.models.Info
import com.example.youtubeparser.ui.adapters.PlayListAdapter
import com.example.youtubeparser.ui.adapters.PlayListViewModel
import kotlinx.android.synthetic.main.activity_playlist.*

class PlayListActivity : BaseActivity(R.layout.activity_playlist) {

    private val adapter = PlayListAdapter(::clickItem)
    private val viewModel: PlayListViewModel by viewModels()
    private val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    private lateinit var alertDialogBuilder: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
        initAdapter()
        listLoadCheker()
    }

    //annonim function
    private fun clickItem(item: Info) {
        var intent = Intent(this, PlayItemsListActivity::class.java)
        intent.putExtra("ID", item.id)
        intent.putExtra("TITLE", item.snippet.title)
        intent.putExtra("DESC", item.snippet.description)
        startActivity(intent)
    }

    private fun setupObservers() {
        viewModel.fetchPlaylist()?.observe(this, Observer {
            adapter.addItems(it.items)
        })
    }

    private fun listLoadCheker() {
        if (isNetworkAvailable()) {
            setupObservers()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Network connection error")
            builder.setMessage("Try again")
            builder.setPositiveButton("refresh") { dialog, which ->
                if (isNetworkAvailable()) {
                    setupObservers()
                } else {
                    listLoadCheker()
                }
            }
            builder.show()
        }
    }

    fun initAdapter() {
        rv_playlist.adapter = adapter
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_playlist.layoutManager = layoutManager
        rv_playlist.itemAnimator = DefaultItemAnimator()
        rv_playlist.adapter = adapter
        rv_playlist.isNestedScrollingEnabled = true
        val intent = Intent(this, PlayItemsListActivity::class.java)
        rv_playlist.setOnClickListener {
            startActivity(intent)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}