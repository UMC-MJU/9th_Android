package com.example.week2_flo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week2_flo.HomeBannerUi
import com.example.week2_flo.databinding.ItemHomeBannerBinding

class HomeBannerAdapter(
    private val items: List<HomeBannerUi>
) : RecyclerView.Adapter<HomeBannerAdapter.VH>() {

    inner class VH(private val b: ItemHomeBannerBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(ui: HomeBannerUi) = with(b) {
            homePannelBackgroundIv.setImageResource(ui.backgroundRes)
            homePannelTitleTv.text = ui.title
            homePannelAlbumInfo01Tv.text = ui.infoText

            homePannelAlbumImg01Iv.setImageResource(ui.track1Thumb)
            homePannelAlbumTitle01Tv.text = ui.track1Title
            homePannelAlbumSinger02Tv.text = ui.track1Artist

            homePannelAlbumImg02Iv.setImageResource(ui.track2Thumb)
            homePannelAlbumTitle03Tv.text = ui.track2Title
            homePannelAlbumSinger04Tv.text = ui.track2Artist
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inf = LayoutInflater.from(parent.context)
        val binding = ItemHomeBannerBinding.inflate(inf, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}