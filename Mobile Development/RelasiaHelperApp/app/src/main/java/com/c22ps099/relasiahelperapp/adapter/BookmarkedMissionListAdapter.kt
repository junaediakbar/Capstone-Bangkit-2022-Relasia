package com.c22ps099.relasiahelperapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.databinding.ItemMissionSmallBinding
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.utils.DateFormatter

class BookmarkedMissionListAdapter(private val listMissionMark: ArrayList<MissionDataItem>) :
    RecyclerView.Adapter<BookmarkedMissionListAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemMissionSmallBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMissionSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var (
            endDate,
            address,
            city,
            title,
            featuredImage,
            helpseeker,
            numberOfNeeds,
            note,
            requirement,
            province,
            id,
            category,
            startDate,
            timestamp,
            volunteers
        ) = listMissionMark[position]
        holder.binding.apply {
            val url = if(featuredImage?.size != 0) {
                featuredImage?.get(0)
            } else {
                listOf("")
                featuredImage = listOf("")
            }
            Glide.with(itemView.context)
                .load(url)
                .placeholder(R.drawable.no_image_placeholder)
                .into(ivMissionPhoto)
            tvMissionTitle.text = title
            tvMissionCity.text = "$city, $province"
            tvMissionDate.text = DateFormatter.formatDate(startDate) + " - " + DateFormatter.formatDate(endDate)
            tvApplicant.text = numberOfNeeds
            tvMissionCategory.text = category
        }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMissionMark[holder.bindingAdapterPosition]) }
    }

    override fun getItemCount(): Int = listMissionMark.size

    interface OnItemClickCallback {
        fun onItemClicked(data: MissionDataItem)
    }
}