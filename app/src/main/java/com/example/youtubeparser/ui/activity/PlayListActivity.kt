package com.example.youtubeparser.ui.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeparser.R
import com.example.youtubeparser.base.BaseActivity
import com.example.youtubeparser.data.network.Status
import com.example.youtubeparser.models.Info
import com.example.youtubeparser.ui.adapters.PlayListAdapter
import com.example.youtubeparser.ui.adapters.PlayListViewModel
import kotlinx.android.synthetic.main.activity_playlist.*
import kotlinx.coroutines.launch

class PlayListActivity : BaseActivity(R.layout.activity_playlist) {

    private val adapter = PlayListAdapter(::clickItem)
    private val viewModel: PlayListViewModel by viewModels()
    private val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    private lateinit var alertDialogBuilder: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
        initAdapter()
        setRecyclerViewScrollListener()
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
        lifecycleScope.launch {
            viewModel.fetchPlaylist()?.observe(this@PlayListActivity, Observer {
                when(it.status) {
                    Status.SUCCESS ->{
                        adapter.addItems(it.data?.items!!)
                    }
                    Status.LOADING -> {
                        Log.e("TAG", "setupObservers: ")
                    }
                    Status.ERROR -> {
                        Log.e("TAG", "setupObservers: ")
                    }
                }

            })
        }
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

    private fun setRecyclerViewScrollListener() {
        rv_playlist.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = rv_playlist.getLayoutManager() as LinearLayoutManager
                val pos = layoutManager.findLastCompletelyVisibleItemPosition()
                val numItems: Int = rv_playlist.getAdapter()!!.getItemCount()
                if (pos+1 == numItems) {
                    Log.e("TAG", "onScrollStateChanged: " + pos)
                }
            }
        })
    }



    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}