package com.example.myapplication22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication22.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator // Added import

class LockerFragment : Fragment() {

    private var _binding: FragmentLockerBinding? = null
    private val binding get() = _binding!!

    private val tabTitles = arrayListOf("저장한 곡", "음악파일", "저장앨범")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLockerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup ViewPager2 adapter
        val lockerAdapter = LockerViewPagerAdapter(this) // Pass 'this' as the parent Fragment
        binding.lockerViewpager.adapter = lockerAdapter

        // Link TabLayout with ViewPager2 using TabLayoutMediator
        TabLayoutMediator(binding.lockerTabs, binding.lockerViewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}