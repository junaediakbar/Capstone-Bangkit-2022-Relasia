package com.c22ps099.relasiahelperapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.databinding.ItemMissionStatusBinding
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.utils.DateFormatter
import com.c22ps099.relasiahelperapp.utils.toTitleCase

class MissionFilterAdapter(private val listMission: ArrayList<MissionDataItem>, private val volunteerId: String) :
    RecyclerView.Adapter<MissionFilterAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemMissionStatusBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMissionStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            for (volunteer in volunteers) {
                if (volunteer.id == volunteerId) {
                    tvMissionStatus.text = toTitleCase(volunteer.status)
                    tvMissionStatus.apply {
                        when (volunteer.status) {
                            "pending" -> setBackgroundResource(R.drawable.status_border_pending)
                            "accepted" -> setBackgroundResource(R.drawable.status_border_accept)
                            "rejected" -> setBackgroundResource(R.drawable.status_border_reject)
                        }
                    }
                    tvMissionStatus.apply {
                        when (volunteer.status) {
                            "pending" -> setTextColor(Color.rgb(199, 158, 55))
                            "accepted" -> setTextColor(Color.rgb(0, 191, 166))
                            "rejected" -> setTextColor(Color.rgb(201, 16, 82))
                        }
                    }
                }
            }
        }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMission[holder.bindingAdapterPosition]) }
    }

    override fun getItemCount(): Int = listMission.size

    interface OnItemClickCallback {
        fun onItemClicked(data: MissionDataItem)
    }
}