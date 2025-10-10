package com.example.myapplication22

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUM_TABS = 3

class AlbumViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AlbumTracksFragment.newInstance()
            1 -> AlbumDetailFragment.newInstance()
            2 -> AlbumVideoFragment.newInstance()
            else -> throw IllegalStateException("Invalid adapter position")
        }
    }
}
