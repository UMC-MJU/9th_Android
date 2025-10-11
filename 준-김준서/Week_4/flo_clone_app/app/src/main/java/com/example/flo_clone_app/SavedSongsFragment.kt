package com.example.flo_clone_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_clone_app.adapter.SongRVAdapter
import com.example.flo_clone_app.data.Song
import com.example.flo_clone_app.databinding.FragmentSavedSongsBinding

// (1) 인터페이스를 상속받도록 추가
class SavedSongsFragment : Fragment(), SongRVAdapter.MyItemClickListener {
    lateinit var binding: FragmentSavedSongsBinding
    private var songDatas = ArrayList<Song>()
    // 어댑터 변수를 멤버 변수로 빼서, 나중에 notifyItemRemoved를 호출할 때 사용
    lateinit var songRVAdapter: SongRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongsBinding.inflate(inflater, container, false)

        songDatas.apply {
            add(Song("Butter", "방탄소년단"))
            add(Song("Next Level", "aespa"))
            add(Song("Weekend", "태연 (TAEYEON)"))
        }

        // 어댑터 초기화
        songRVAdapter = SongRVAdapter(songDatas)
        binding.savedSongsRv.adapter = songRVAdapter
        binding.savedSongsRv.layoutManager = LinearLayoutManager(context)

        // (2) 어댑터에 리스너 연결
        songRVAdapter.setMyItemClickListener(this)

        return binding.root
    }

    // (3) 인터페이스의 onRemoveSong 함수 구현
    override fun onRemoveSong(position: Int) {
        // 데이터 목록에서 아이템 삭제
        songDatas.removeAt(position)
        // 어댑터에 아이템이 삭제되었다고 알려줌 (애니메이션 효과)
        songRVAdapter.notifyItemRemoved(position)
    }
}