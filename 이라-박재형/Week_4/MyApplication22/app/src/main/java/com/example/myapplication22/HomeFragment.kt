package com.example.myapplication22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication22.databinding.FragmentHomeBinding
import com.example.myapplication22.databinding.ItemAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val musicViewModel: MusicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val bannerAdapter = BannerAdapter(this, musicViewModel)
        binding.homeBannerVp.adapter = bannerAdapter
        TabLayoutMediator(binding.homeBannerIndicator, binding.homeBannerVp) { tab, position ->
        }.attach()

        val albums = listOf(
            Album("Lost corner", "Kenshi Yonezu", R.drawable.img_album3,
                listOf(
                    Track("01", "Lady", "Kenshi Yonezu"),
                    Track("02", "Spinning Globe", "Kenshi Yonezu"),
                    Track("03", "Moongazing", "Kenshi Yonezu")
                )),
            Album("The book 3", "Yoasobi", R.drawable.img_album4,
                listOf(
                    Track("01", "Biri-Biri", "Yoasobi"),
                    Track("02", "Idol", "Yoasobi"),
                    Track("03", "Mister", "Yoasobi")
                )),
            Album("愛を伝えたいだとか", "Aimyon", R.drawable.image__1,
                listOf(
                    Track("01", "Haru no Hi", "Aimyon"),
                    Track("02", "Marigold", "Aimyon"),
                    Track("03", "Her Blue Sky", "Aimyon")
                )),
            Album("Weekend", "TAEYEON", R.drawable.img_lost_corner_placeholder),
            Album("낙하 (with IU)", "AKMU", R.drawable.img_lost_corner_placeholder)
        )

        val albumAdapter = AlbumAdapter(albums, musicViewModel, parentFragmentManager)
        binding.homeTodayMusicAlbumRv.adapter = albumAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class BannerAdapter(fragment: Fragment, private val musicViewModel: MusicViewModel) : FragmentStateAdapter(fragment) {
    private val bannerItems = listOf(
        BannerItemData("달빛의 감성 산책", "총 10곡 2025.03.30", R.drawable.img_first_album_default,
            SongData("Lady", "Kenshi Yonezu", R.drawable.img_album1),
            SongData("Spinning Globe", "Kenshi Yonezu", R.drawable.img_album2))
    )
    override fun getItemCount(): Int = bannerItems.size
    override fun createFragment(position: Int): Fragment {
        return BannerFragment.newInstance(bannerItems[position], musicViewModel)
    }
}

data class BannerItemData(
    val title: String,
    val info: String,
    val backgroundResId: Int,
    val song1: SongData,
    val song2: SongData
)
data class SongData(val title: String, val artist: String, val coverResId: Int)

class AlbumAdapter(
    private val albumList: List<Album>,
    private val musicViewModel: MusicViewModel,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albumList[position]
        holder.bind(album)

        holder.itemView.setOnClickListener {
            musicViewModel.selectAlbum(album) // Use the new selectAlbum function

            val albumFragment = AlbumFragment.newInstance(
                album.title,
                album.artist,
                album.coverImg
            )

            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, albumFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = albumList.size

    class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.artist // Changed from singer
            binding.itemAlbumCoverIv.setImageResource(album.coverImg)
        }
    }
}
