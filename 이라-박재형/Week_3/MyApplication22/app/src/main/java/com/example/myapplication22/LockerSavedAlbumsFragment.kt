package com.example.myapplication22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication22.databinding.FragmentLockerSavedAlbumsBinding

class LockerSavedAlbumsFragment : Fragment() {

    private var _binding: FragmentLockerSavedAlbumsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLockerSavedAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // "저장앨범 목록 화면입니다." 텍스트는 XML에 이미 정의되어 있음
        // 필요에 따라 추가적인 UI 업데이트나 로직을 여기에 구현
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): LockerSavedAlbumsFragment {
            return LockerSavedAlbumsFragment()
        }
    }
}
