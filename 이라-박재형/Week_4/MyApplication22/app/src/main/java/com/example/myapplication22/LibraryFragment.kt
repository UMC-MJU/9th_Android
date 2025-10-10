package com.example.myapplication22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication22.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayout

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabs()
        setupClickListeners()
    }

    private fun setupTabs() {
        binding.libraryTabLayout.addTab(binding.libraryTabLayout.newTab().setText("저장한 곡"))
        binding.libraryTabLayout.addTab(binding.libraryTabLayout.newTab().setText("음악파일"))
        binding.libraryTabLayout.addTab(binding.libraryTabLayout.newTab().setText("저장앨범"))

        binding.libraryTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // TODO: Implement content change based on selected tab (e.g., using ViewPager2 or by replacing a FrameLayout's content)
                Toast.makeText(context, "Selected: ${tab?.text}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun setupClickListeners() {
        binding.libraryLoginBtn.setOnClickListener {
            // TODO: Implement login functionality
            Toast.makeText(context, "로그인 버튼 클릭됨", Toast.LENGTH_SHORT).show()
        }

        binding.libraryEditBtn.setOnClickListener {
            // TODO: Implement edit mode functionality
            Toast.makeText(context, "편집 버튼 클릭됨", Toast.LENGTH_SHORT).show()
        }
        binding.librarySelectAllCb.setOnCheckedChangeListener { _, isChecked ->
            // TODO: Implement select all/deselect all logic for the list of items
            val message = if (isChecked) "전체 선택됨" else "전체 선택 해제됨"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        binding.libraryPlayAllTv.setOnClickListener {
            // TODO: Implement play all functionality for the selected/displayed list
            Toast.makeText(context, "전체 듣기 클릭됨", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}