package com.example.flo_clone_app

import com.google.gson.Gson
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo_clone_app.activity.MainActivity
import com.example.flo_clone_app.adapter.AlbumRVAdapter
import com.example.flo_clone_app.adapter.BannerVPAdapter
import com.example.flo_clone_app.adapter.HomeBannerAdapter
import com.example.flo_clone_app.data.Album
import com.example.flo_clone_app.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBom BBom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{
            // 앨범 커버 클릭 시의 동작
            override fun onGoAlbumClick(album: Album) {
                changeAlbumFragment(album) // 함수 이름 오타 수정
            }

            // 재생 버튼 클릭 시의 동작
            override fun onPlayClick(album: Album) {
                (activity as MainActivity).updateMiniPlayer(album)
            }
        })

        // 1) 상단 슬라이드 배너 (ViewPager2 + RecyclerView.Adapter)
        setupTopHomeBanner()

        // 2) 하단 배너 (기존: ViewPager2 + FragmentStateAdapter)
        setupBottomBanner()

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }

    /** 상단 슬라이드 배너: ViewPager2 + RecyclerView.Adapter(HomeBannerAdapter) */
    private fun setupTopHomeBanner() {
        val topBanners = listOf(
            HomeBannerUi(
                backgroundRes = R.drawable.img_first_album_default,
                title = "달빛의 감성 산책",
                infoText = "총 15곡 2025.09.23",
                track1Thumb = R.drawable.img_album_exp,
                track1Title = "Butter",
                track1Artist = "BTS",
                track2Thumb = R.drawable.img_album_exp2,
                track2Title = "LILAC",
                track2Artist = "아이유(IU)"
            ),
            HomeBannerUi(
                backgroundRes = R.drawable.img_first_album_default, // 샘플
                title = "밤 드라이브 플레이리스트",
                infoText = "총 12곡 2025.08.10",
                track1Thumb = R.drawable.img_album_exp2,
                track1Title = "Love wins all",
                track1Artist = "아이유",
                track2Thumb = R.drawable.img_album_exp,
                track2Title = "Dynamite",
                track2Artist = "BTS"
            )
        )

        binding.homeBannerViewPager.apply {
            adapter = HomeBannerAdapter(topBanners)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            // 필요 시 스크롤 충돌 완화
            // isNestedScrollingEnabled = false
        }

        // 점(Indicator) 연결
        TabLayoutMediator(binding.homeBannerIndicator, binding.homeBannerViewPager) { _, _ -> }.attach()
    }

    /** 하단 배너: 기존 방식 유지 (ViewPager2 + Fragment 어댑터) */
    private fun setupBottomBanner() {
        val bannerAdapter = BannerVPAdapter(this).apply {
            addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
            addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
            addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
            addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
            addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
            addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        }

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
}