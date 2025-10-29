package com.example.flo_clone_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo_clone_app.adapter.LockerVPAdapter
import com.example.flo_clone_app.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    private val information = arrayListOf("저장한 곡", "음악파일", "저장앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerVPAdapter(this)

        // ViewPager2의 ID를 'locker_vp'로 수정
        binding.lockerVp.adapter = lockerAdapter

        // TabLayout의 ID를 'locker_tab'으로, ViewPager2의 ID를 'locker_vp'로 수정
        TabLayoutMediator(binding.lockerTab, binding.lockerVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
}