package com.example.flo_clone_app

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_clone_app.R
import com.example.flo_clone_app.adapter.SavedAlbumAdapter
import com.example.flo_clone_app.data.SavedAlbum
import com.example.flo_clone_app.databinding.FragmentSavedAlbumsBinding

class SavedAlbumsFragment : Fragment(), SavedAlbumAdapter.Listener {

    private var _binding: FragmentSavedAlbumsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SavedAlbumAdapter
    private var albums = mutableListOf(
        SavedAlbum(1, "GREAT!", "모모랜드", "2021.03.25", "댄스"),
        SavedAlbum(2, "Butter", "BTS", "2021.03.25", "댄스 팝")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSavedAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SavedAlbumAdapter(this)
        binding.rvSavedAlbums.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSavedAlbums.adapter = adapter
        binding.rvSavedAlbums.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        adapter.submitList(albums.toList())
    }

    // Listener 구현
    override fun onPlayPauseClick(item: SavedAlbum) {
        albums = albums.map { a ->
            if (a.id == item.id) a.copy(isPlaying = !item.isPlaying) else a.copy(isPlaying = false)
        }.toMutableList()
        adapter.submitList(albums.toList())
    }

    override fun onOverflowClick(anchor: View, item: SavedAlbum) {
        val popup = PopupMenu(requireContext(), anchor)
        popup.menuInflater.inflate(R.menu.menu_saved_album_item, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_delete -> {
                    albums.removeAll { a -> a.id == item.id }
                    adapter.submitList(albums.toList())
                    Toast.makeText(requireContext(), "삭제했어요", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    override fun onItemClick(item: SavedAlbum) {
        Toast.makeText(requireContext(), "앨범 열기: ${item.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}