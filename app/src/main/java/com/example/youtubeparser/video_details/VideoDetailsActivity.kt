package com.example.youtubeparser.video_details


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.youtubeparser.R
import com.example.youtubeparser.constants.Constants
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_video_details.*
import java.lang.String


class VideoDetailsActivity : YouTubeBaseActivity(),YouTubePlayer.OnInitializedListener {

    lateinit var videoId: kotlin.String
    lateinit var videoTitle: kotlin.String
    lateinit var videoDesc: kotlin.String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_details)
        videoId = intent.getStringExtra("ID") ?: ""
        videoTitle = intent.getStringExtra("TIT") ?:""
        videoDesc = intent.getStringExtra("DES") ?:""
        setupViews()
    }

    private fun setupViews() {
        youtube_view.initialize(Constants.API_KEY,this)
        vd_title_tv.setText(videoTitle)
        vd_desc_tv.setText(videoDesc)
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        if(!wasRestored){
            player?.cueVideo(videoId)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        errorReason: YouTubeInitializationResult
    ) {
        if (errorReason.isUserRecoverableError) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show()
        } else {
            val error =
                String.format(getString(R.string.player_error), errorReason.toString())
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == RECOVERY_REQUEST) {
            youtube_view.initialize(Constants.API_KEY, this)
        }
    }

    companion object{
        private const val RECOVERY_REQUEST = 1

    }

}
