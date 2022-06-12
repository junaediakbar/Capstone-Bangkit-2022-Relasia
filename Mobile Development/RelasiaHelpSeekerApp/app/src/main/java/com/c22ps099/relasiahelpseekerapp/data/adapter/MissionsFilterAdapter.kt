package com.c22ps099.relasiahelpseekerapp.data.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.databinding.ItemMissionStatusBinding
import com.c22ps099.relasiahelpseekerapp.databinding.ItemPostBinding
import com.c22ps099.relasiahelpseekerapp.misc.DateFormatter
import java.lang.Character.toTitleCase

class MissionsFilterAdapter(
    private val listMission: ArrayList<MissionItem>,private val status: String
) :
    RecyclerView.Adapter<MissionsFilterAdapter.MyViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemMissionStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding,status)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listMission[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMission[holder.bindingAdapterPosition]) }
    }

    class MyViewHolder(private var binding: ItemMissionStatusBinding,private val status: String) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mission: MissionItem) {

            val url = if(mission.featuredImage?.size != 0) {
                mission.featuredImage?.get(0)
            } else {
                listOf("")
                mission.featuredImage = listOf("")
            }
            binding.apply {
                Glide.with(itemView.context)
                    .load(url)
                    .placeholder(R.drawable.no_image_placeholder)
                    .into(ivMissionPhoto)
                tvMissionTitle.text = mission.title
                tvMissionCity.text = "${mission.city}, ${mission.province}"
                tvMissionDate.text = DateFormatter.formatDate(mission.startDate) + " - " + DateFormatter.formatDate(mission.endDate)
                tvMissionStatus.text = status
            }
        }
    }

    override fun getItemCount(): Int = listMission.size

    interface OnItemClickCallback {
        fun onItemClicked(data: MissionItem)
    }
}