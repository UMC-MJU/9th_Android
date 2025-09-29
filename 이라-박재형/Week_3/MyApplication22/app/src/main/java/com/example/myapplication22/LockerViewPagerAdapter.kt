package com.example.myapplication22

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    // These fragments will correspond to the tabs: "저장한 곡", "음악파일", "저장앨범"
    private val fragments = listOf(
        SavedSongsFragment(),
        PlaylistsFragment(),
        LikedAlbumsFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}