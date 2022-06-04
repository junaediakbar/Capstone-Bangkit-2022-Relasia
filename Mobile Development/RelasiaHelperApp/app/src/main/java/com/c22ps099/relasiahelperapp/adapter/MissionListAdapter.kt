package com.c22ps099.relasiahelperapp.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.databinding.ItemMissionBinding
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.ui.missionDetail.MissionDetailFragment
import com.c22ps099.relasiahelperapp.utils.DateFormatter


class MissionListAdapter :
    PagingDataAdapter<MissionDataItem, MissionListAdapter.MissionHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MissionDataItem>() {
            override fun areItemsTheSame(oldItem: MissionDataItem, newItem: MissionDataItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MissionDataItem, newItem: MissionDataItem) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionHolder {
        val itemBinding =
            ItemMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MissionHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MissionHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MissionHolder(private val binding: ItemMissionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MissionDataItem) {
            binding.apply {
                tvMissionTitle.text = data.title
                tvMissionCity.text = data.city + ", " + data.province
                tvMissionDate.text = DateFormatter.formatDate(data.startDate) + " - " + DateFormatter.formatDate(data.endDate)
                tvApplicant.text = data.numberOfNeeds
            }
            val url = data.featuredImage[0]
            Glide.with(binding.ivMissionPhoto.context)
                .load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(binding.ivMissionPhoto)

            binding.btnDetails.setOnClickListener {
                val bundle = bundleOf(MissionDetailFragment.EXTRA_MISSION to data)
                itemView.findNavController().navigate(R.id.missionDetailFragment, bundle)
            }
        }
    }
}