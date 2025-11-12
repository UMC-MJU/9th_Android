
package com.example.myapplication.savedAlbum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.model.Album
import com.example.model.TitleItem
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSavedAlbumBinding
import com.example.myapplication.savedAlbum.adapter.AlbumLockerRVAdapter

class SavedAlbumFragment : Fragment() {

    lateinit var binding: FragmentSavedAlbumBinding
    var list = getDummy()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedAlbumBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerSavedSongRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val albumRVAdapter = AlbumLockerRVAdapter(list)

        albumRVAdapter.setMyItemClickListener(object : AlbumLockerRVAdapter.MyItemClickListener{
            override fun onRemoveAlbum(position: Int) {
                //TODO 나중에 db같은거 연결하면 완전한 삭제 구현
                albumRVAdapter.removeItem(position)
            }
        })

        binding.lockerSavedSongRecyclerView.adapter = albumRVAdapter

    }

    private fun getDummy() : ArrayList<Album> {
        return arrayListOf(
            Album(
                id = 0,
                title = "LILAC",
                singer = "아이유 (IU)",
                coverImg = R.drawable.img_album_exp2
            ),
            Album(
                id = 1,
                title = "LILAC",
                singer = "아이유 (IU)",
                coverImg = R.drawable.img_album_exp2
            ),
            Album(
                id = 2,
                title = "LILAC",
                singer = "아이유 (IU)",
                coverImg = R.drawable.img_album_exp2
            )
        )
    }

}