package com.example.week2_flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.week2_flo.activity.MainActivity
import com.example.week2_flo.adapter.BannerVPAdapter
import com.example.week2_flo.adapter.HomeBannerAdapter
import com.example.week2_flo.data.Song
import com.example.week2_flo.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 앨범 상세 이동 (기존 코드 유지)
        binding.homeAlbumImgIv1.setOnClickListener {
            (requireActivity() as MainActivity)
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frm, AlbumFragment())
                .commitAllowingStateLoss()
        }

        // 1) 상단 슬라이드 배너 (ViewPager2 + RecyclerView.Adapter)
        setupTopHomeBanner()

        // 2) 하단 배너 (기존: ViewPager2 + FragmentStateAdapter)
        setupBottomBanner()

        return binding.root
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