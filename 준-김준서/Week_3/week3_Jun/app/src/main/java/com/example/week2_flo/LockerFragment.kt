package com.example.week2_flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.week2_flo.adapter.LockerVPAdapter
import com.example.week2_flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {
    private lateinit var b: FragmentLockerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        b = FragmentLockerBinding.inflate(inflater, container, false)

        b.lockerVp.adapter = LockerVPAdapter(this)
        b.lockerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val titles = arrayOf("저장한 곡", "음악파일", "저장앨범")
        TabLayoutMediator(b.lockerTab, b.lockerVp) { tab, pos ->
            tab.text = titles[pos]
        }.attach()

        return b.root
    }
}