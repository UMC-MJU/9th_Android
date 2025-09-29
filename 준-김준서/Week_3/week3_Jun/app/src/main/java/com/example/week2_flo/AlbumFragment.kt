package com.example.week2_flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.week2_flo.activity.MainActivity
import com.example.week2_flo.adapter.AlbumVPAdapter
import com.example.week2_flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment(){

    lateinit var binding : FragmentAlbumBinding

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        val albumVPAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumVPAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, positon ->
            tab.text = information[positon]
        }.attach()

        return binding.root
    }
}