package com.sagarmakhija1994.dgnadmin.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sagarmakhija1994.dgnadmin.R
import com.sagarmakhija1994.dgnadmin.databinding.ActivityHomeBinding
import com.sagarmakhija1994.dgnadmin.ui.login.LoginActivity
import com.sagarmakhija1994.dgnadmin.ui.videos.KirtanVideosActivity
import com.sagarmakhija1994.dgnadmin.util.AppLevelData
import com.sagarmakhija1994.dgnadmin.util.enums.VideoListSelectionTypeEnum

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        binding.btnLive.setOnClickListener {
            AppLevelData.videoSelectionListType = VideoListSelectionTypeEnum.LIVE
            startActivity(Intent(this, KirtanVideosActivity::class.java))
        }

        binding.btnKirtanVideos.setOnClickListener {
            AppLevelData.videoSelectionListType = VideoListSelectionTypeEnum.VIDEOS
            startActivity(Intent(this, KirtanVideosActivity::class.java))
        }

        binding.btnHeaderImages.setOnClickListener {
            Toast.makeText(this,"Coming Soon...",Toast.LENGTH_LONG).show()
        }
    }
}