package com.example.youtubeparser.ui.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeparser.R
import com.example.youtubeparser.base.BaseActivity
import com.example.youtubeparser.ui.adapters.PlayItemsListAdapter
import com.example.youtubeparser.ui.adapters.PlayItemsListViewModel
import com.example.youtubeparser.ui.adapters.PlayListAdapter
import com.example.youtubeparser.ui.adapters.PlayListViewModel
import com.example.youtubeparser.video_details.VideoDetailsActivity
import kotlinx.android.synthetic.main.activity_play_items_list.*
import kotlinx.android.synthetic.main.activity_playlist.*

class PlayItemsListActivity : BaseActivity(R.layout.activity_play_items_list), PlayItemsListAdapter.Listener {

    private lateinit var adapter: PlayItemsListAdapter
    private val viewModel: PlayItemsListViewModel by viewModels()
    private val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    private lateinit var  alertDialogBuilder: AlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var id: String? = intent.getStringExtra("ID")
        initAdapter()
        setupObservers(id)
        workWithToolBar()

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
        if (id != null) {
            viewModel.fetchPlayItems(id)?.observe(this, Observer {
                adapter.addItems(it.items)
            })
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
        float_btn.setOnClickListener {
            startActivity(intent)
        }
    }
}