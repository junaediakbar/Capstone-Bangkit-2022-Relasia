package com.c22ps099.relasiahelperapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.databinding.ItemMissionStatusBinding
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.ui.missionDetail.MissionDetailFragment
import com.c22ps099.relasiahelperapp.utils.DateFormatter
import com.c22ps099.relasiahelperapp.utils.toTitleCase
import java.util.*

class AppliedMissionListAdapter(
    private val volunteerId: String
) : PagingDataAdapter<MissionDataItem, AppliedMissionListAdapter.MissionStatusHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MissionDataItem>() {
            override fun areItemsTheSame(oldItem: MissionDataItem, newItem: MissionDataItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MissionDataItem, newItem: MissionDataItem) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionStatusHolder {
        val binding =
            ItemMissionStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MissionStatusHolder(binding, volunteerId)
    }

    override fun onBindViewHolder(holder: MissionStatusHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MissionStatusHolder(
        var binding: ItemMissionStatusBinding,
        private val volunteerId: String
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MissionDataItem) {
            binding.apply {
                val url = if(data.featuredImage?.size != 0) {
                    data.featuredImage?.get(0)
                } else {
                    listOf("")
                    data.featuredImage = listOf("")
                }
                Glide.with(itemView.context)
                    .load(url)
                    .placeholder(R.drawable.no_image_placeholder)
                    .into(ivMissionPhoto)
                tvMissionTitle.text = data.title
//                tvMissionCity.text = "$data.city, $data.province"
                tvMissionDate.text =
                    DateFormatter.formatDate(data.startDate) + " - " + DateFormatter.formatDate(
                        data.endDate
                    )
                for (volunteer in data.volunteers) {
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
            binding.itemView.setOnClickListener {
                val bundle = bundleOf(MissionDetailFragment.EXTRA_MISSION to data)
                itemView.findNavController().navigate(R.id.missionDetailFragment, bundle)
            }
        }
    }
}