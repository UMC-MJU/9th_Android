package com.example.myapplication22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels // Import for activityViewModels
import com.example.myapplication22.databinding.FragmentAlbumDetailBinding

class AlbumDetailFragment : Fragment() {

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!

    // Get a reference to the shared MusicViewModel
    private val musicViewModel: MusicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the selectedAlbum LiveData from the ViewModel
        musicViewModel.selectedAlbum.observe(viewLifecycleOwner) { album ->
            // Update the UI when the selected album changes
            // The 'album' parameter here is of type com.example.myapplication22.Album?
            // (nullable because selectedAlbum in ViewModel is LiveData<Album?>)
            if (album != null) {
                binding.albumDetailAlbumTitleTv.text = album.title
                binding.albumDetailArtistNameTv.text = album.artist
            } else {
                // Handle the case where no album is selected, if necessary
                binding.albumDetailAlbumTitleTv.text = "N/A" // Or some default text
                binding.albumDetailArtistNameTv.text = "N/A"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): AlbumDetailFragment {
            return AlbumDetailFragment()
        }
    }
}
