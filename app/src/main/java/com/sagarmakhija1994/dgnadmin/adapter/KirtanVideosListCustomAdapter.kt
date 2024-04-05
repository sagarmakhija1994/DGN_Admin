package com.sagarmakhija1994.dgnadmin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.sagarmakhija1994.dgnadmin.databinding.LayoutListItemKirtanVideosBinding
import com.sagarmakhija1994.dgnadmin.model.KirtanVideoItemDataModel
import com.sagarmakhija1994.dgnadmin.util.Utils
import io.reactivex.annotations.NonNull


class KirtanVideosListCustomAdapter(private val data: ArrayList<KirtanVideoItemDataModel>, private val onClick: (KirtanVideoItemDataModel) -> Unit) : RecyclerView.Adapter<KirtanVideosListCustomAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: LayoutListItemKirtanVideosBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutListItemKirtanVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        val binding = holder.binding
        val videoID = Utils.extractYTID(data[i].url)
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoID, 0f)
                youTubePlayer.play()
            }
        })
        binding.clickViewWindow.setOnClickListener {
            onClick(data[i])
        }

        binding.root.setOnClickListener{
            onClick(data[i])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}