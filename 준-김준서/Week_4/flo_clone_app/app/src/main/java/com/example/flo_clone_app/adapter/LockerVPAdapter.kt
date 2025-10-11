package com.example.flo_clone_app.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo_clone_app.LocalFilesFragment
import com.example.flo_clone_app.SavedAlbumsFragment
import com.example.flo_clone_app.SavedSongsFragment

class LockerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> SavedSongsFragment()   // 저장한 곡
        1 -> LocalFilesFragment()   // 음악파일
        else -> SavedAlbumsFragment() // 저장앨범
    }
}
