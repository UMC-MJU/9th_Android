package com.example.myapplication22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication22.databinding.FragmentLikedAlbumsBinding

class LikedAlbumsFragment : Fragment() {

    private var _binding: FragmentLikedAlbumsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikedAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // You can initialize UI elements or load data for liked albums here
        // For example:
        // binding.textViewInLikedAlbums.text = "My Liked Albums"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}