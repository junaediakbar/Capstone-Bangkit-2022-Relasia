package com.c22ps099.relasiahelperapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.databinding.ItemMissionBinding
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.utils.DateFormatter

class MissionSearchAdapter(private val listMission: ArrayList<MissionDataItem>) :
    RecyclerView.Adapter<MissionSearchAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemMissionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
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
        ) = listMission[position]
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
        holder.binding.btnDetails.setOnClickListener { onItemClickCallback.onItemClicked(listMission[holder.bindingAdapterPosition]) }
    }

    override fun getItemCount(): Int = listMission.size

    interface OnItemClickCallback {
        fun onItemClicked(data: MissionDataItem)
    }
}