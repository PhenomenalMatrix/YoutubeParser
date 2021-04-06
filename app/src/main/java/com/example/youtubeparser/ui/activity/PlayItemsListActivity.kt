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
import com.example.youtubeparser.ui.adapters.PlayItemsListAdapter
import com.example.youtubeparser.ui.adapters.PlayItemsListViewModel
import com.example.youtubeparser.video_details.VideoDetailsActivity
import kotlinx.android.synthetic.main.activity_play_items_list.*
import kotlinx.android.synthetic.main.activity_playlist.*
import kotlinx.coroutines.launch

class PlayItemsListActivity : BaseActivity(R.layout.activity_play_items_list), PlayItemsListAdapter.Listener {

    private lateinit var adapter: PlayItemsListAdapter
    private val viewModel: PlayItemsListViewModel by viewModels()
    private val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    private lateinit var  alertDialogBuilder: AlertDialog
    private var list : MutableList<Info> = mutableListOf()
    private lateinit var id: String
    private var nextPageToken = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getStringExtra("ID").toString()
        initAdapter()
        setupObservers(id)
        workWithToolBar()
        float_btn.setOnClickListener {
            val intent = Intent(this,VideoDetailsActivity::class.java)
            intent.putExtra("IDV",list.first().contentDetails?.videoId)
            intent.putExtra("TITL", list.first().snippet.title)
            intent.putExtra("DESC",list.first().snippet.description)
            startActivity(intent)
        }
        setRecyclerViewScrollListener()

    }

    private fun workWithToolBar() {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { finish() }
        var title = intent.getStringExtra("TITLE")
        var desc = intent.getStringExtra("DESC")
        toolbar.setTitle(title)
        title_tv.setText(title)
        desc_tv.setText(desc)
    }


    private fun setupObservers(id: String?) {
        lifecycleScope.launch {
            if (id != null) {
                viewModel.fetchPlayItems(id,nextPageToken)?.observe(this@PlayItemsListActivity, Observer {
                    when(it.status) {
                        Status.SUCCESS -> {
                            adapter.addItems(it.data?.items!!)
                            nextPageToken = it.data.nextPageToken.toString()
                            list.addAll(it.data?.items!!)
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

    }


    fun initAdapter() {
        adapter = PlayItemsListAdapter(this)
        rv_playitemlist.adapter = adapter
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_playitemlist.layoutManager = layoutManager
        rv_playitemlist.itemAnimator = DefaultItemAnimator()
        rv_playitemlist.adapter = adapter
        rv_playitemlist.isNestedScrollingEnabled = true
    }

    private fun setRecyclerViewScrollListener() {
        rv_playitemlist.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = rv_playitemlist.getLayoutManager() as LinearLayoutManager
                val pos = layoutManager.findLastCompletelyVisibleItemPosition()
                val numItems: Int = rv_playitemlist.getAdapter()!!.getItemCount()
                if (pos+1 == numItems) {
                    setupObservers(id)
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


    override fun onItemClicked(id: String, title: String, desc: String) {
        val intent = Intent(this,VideoDetailsActivity::class.java)
        intent.putExtra("ID",id)
        intent.putExtra("TIT", title)
        intent.putExtra("DES",desc)
        startActivity(intent)


    }
}