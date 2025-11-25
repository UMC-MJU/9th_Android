package com.example.myapplication.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.TitleItem
import com.example.myapplication.databinding.ItemHomePagerBinding
import java.util.ArrayList

class HomeTitleAdapter(
    private val titleList: ArrayList<TitleItem>
) : RecyclerView.Adapter<HomeTitleViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HomeTitleViewHolder {
        val binding: ItemHomePagerBinding = ItemHomePagerBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return HomeTitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeTitleViewHolder, position: Int) {
        holder.bind(titleList[position])
    }

    override fun getItemCount(): Int = titleList.size
}