package com.example.myapplication22

import android.content.Intent // Intent import 추가
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
// import androidx.fragment.app.activityViewModels // 필요시 주석 해제
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication22.databinding.FragmentLockerSavedSongsBinding

class LockerSavedSongsFragment : Fragment() {

    private var _binding: FragmentLockerSavedSongsBinding? = null
    private val binding get() = _binding!!

    // private val musicViewModel: MusicViewModel by activityViewModels() // 필요시 주석 해제

    private lateinit var savedSongsAdapter: TrackAdapter
    private val savedSongsList = mutableListOf<Track>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLockerSavedSongsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadSavedSongs()

        binding.lockerSavedSongsSelectAllCb.setOnCheckedChangeListener { _, isChecked ->
            // 전체 선택 로직
        }
        binding.lockerSavedSongsPlayAllTv.setOnClickListener {
            // 전체 듣기 로직
        }
        binding.lockerSavedSongsEditBtn.setOnClickListener {
            // 편집 모드 로직
        }
    }

    private fun setupRecyclerView() {
        // 임시 플레이스홀더 커버 이미지 ID
        val placeholderCoverResId = R.drawable.img_album_exp // 실제 프로젝트에 있는 drawable 사용

        // 아이템 클릭 시 SongActivity를 시작하는 람다
        val onItemClickedLambda: (Track, Int) -> Unit = { clickedTrack, coverResId ->
            val intent = Intent(activity, SongActivity::class.java).apply {
                putExtra(MainActivity.EXTRA_SONG_TITLE, clickedTrack.title)
                putExtra(MainActivity.EXTRA_SONG_ARTIST, clickedTrack.artist)
                // 저장된 곡의 경우, 실제 커버 ID를 Track 객체에 저장하거나 다른 방식으로 가져와야 할 수 있음
                // 여기서는 어댑터의 플레이스홀더 ID를 사용
                putExtra(SongActivity.EXTRA_ALBUM_COVER_RES_ID, coverResId)
            }
            startActivity(intent)
        }

        savedSongsAdapter = TrackAdapter(savedSongsList, placeholderCoverResId, onItemClickedLambda)
        binding.lockerSavedSongsRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = savedSongsAdapter
        }
    }

    private fun loadSavedSongs() {
        // TODO: 실제 저장된 곡 데이터를 불러오는 로직 (예: ViewModel, SharedPreferences, DB 등)
        savedSongsList.clear()
        // 예시 데이터 (Track 클래스는 number, title, artist를 가짐)
        savedSongsList.add(Track("01", "저장된 첫번째 곡", "아티스트1"))
        savedSongsList.add(Track("02", "저장된 두번째 곡", "아티스트2"))
        savedSongsList.add(Track("03", "저장된 세번째 곡", "아티스트3"))

        if (::savedSongsAdapter.isInitialized) {
            savedSongsAdapter.notifyDataSetChanged()
        }
        updateEmptyViewVisibility()
    }

    private fun updateEmptyViewVisibility() {
        if (savedSongsList.isEmpty()) {
            binding.lockerSavedSongsRv.visibility = View.GONE
            binding.lockerSavedSongsEmptyTv.visibility = View.VISIBLE
        } else {
            binding.lockerSavedSongsRv.visibility = View.VISIBLE
            binding.lockerSavedSongsEmptyTv.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): LockerSavedSongsFragment {
            return LockerSavedSongsFragment()
        }
    }
}
