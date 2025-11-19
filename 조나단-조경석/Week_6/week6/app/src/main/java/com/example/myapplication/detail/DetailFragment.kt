package com.example.myapplication.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentDetailBinding

class DetailFragment(
    private val title: String,
    private val singer: String
) : Fragment() {

    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)

        binding.apply {
            textDetailContent.text = "이 앨범의 이름은 $title 입니다.\n이 앨범의 작곡가는 $singer 입니다."
        }

        return binding.root
    }
}