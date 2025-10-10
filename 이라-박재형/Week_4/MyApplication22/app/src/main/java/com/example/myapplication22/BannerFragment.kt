package com.example.myapplication22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication22.databinding.ItemBannerBinding

class BannerFragment : Fragment() {

    private var _binding: ItemBannerBinding? = null
    private val binding get() = _binding!!

    private var bannerItemData: BannerItemData? = null
    private var musicViewModel: MusicViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ItemBannerBinding.inflate(inflater, container, false)

        // The bannerItemData and musicViewModel are now passed directly via newInstance
        // No need to retrieve from arguments bundle if passed directly as properties.
        
        bannerItemData?.let { currentItemData -> // Changed 'it' to 'currentItemData' for clarity
            binding.itemBannerBackgroundIv.setImageResource(currentItemData.backgroundResId)
            binding.itemBannerTitleTv.text = currentItemData.title
            binding.itemBannerInfoTv.text = currentItemData.info

            binding.homePannelAlbumImgIv.setImageResource(currentItemData.song1.coverResId)
            binding.homePannelAlbumTitleTv.text = currentItemData.song1.title
            binding.homePannelAlbumSingerTv.text = currentItemData.song1.artist

            binding.homePannelAlbumImgIv2.setImageResource(currentItemData.song2.coverResId)
            binding.homePannelAlbumTitleTv2.text = currentItemData.song2.title
            binding.homePannelAlbumSingerTv2.text = currentItemData.song2.artist

            // Click listener for Song 1 (Lady)
            // You might want to make a larger area clickable, e.g., a ConstraintLayout holding the image and text
            binding.homePannelAlbumImgIv.setOnClickListener { // 'it' here is the clicked View (homePannelAlbumImgIv)
                musicViewModel?.selectedSongTitle?.value = currentItemData.song1.title
                musicViewModel?.selectedSongArtist?.value = currentItemData.song1.artist
            }
            // If you want text clicks also:
            // binding.homePannelAlbumTitleTv.setOnClickListener { /* ... */ }
            // binding.homePannelAlbumSingerTv.setOnClickListener { /* ... */ }

            // Click listener for Song 2 (Spinning Globe)
            binding.homePannelAlbumImgIv2.setOnClickListener { // 'it' here is the clicked View (homePannelAlbumImgIv2)
                musicViewModel?.selectedSongTitle?.value = currentItemData.song2.title
                musicViewModel?.selectedSongArtist?.value = currentItemData.song2.artist
            }
        }

        return binding.root
    }

    companion object {
        // Removed ARG_BANNER_ITEM_DATA_TITLE as we are passing objects directly

        fun newInstance(itemData: BannerItemData, viewModel: MusicViewModel): BannerFragment {
            val fragment = BannerFragment()
            fragment.bannerItemData = itemData // Pass data directly
            fragment.musicViewModel = viewModel
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}