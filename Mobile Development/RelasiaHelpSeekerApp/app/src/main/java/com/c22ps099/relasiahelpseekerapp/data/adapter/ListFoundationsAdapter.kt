package com.c22ps099.relasiahelpseekerapp.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.Foundation
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.databinding.ItemPostBinding
import com.c22ps099.relasiahelpseekerapp.ui.missionDetail.MissionDetailFragment

class ListFoundationsAdapter(private var listFoundations: List<Foundation>) :
    RecyclerView.Adapter<ListFoundationsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listFoundations[position])
    }

    override fun getItemCount() = listFoundations.size

    class MyViewHolder(private var binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(foundation: Foundation) {
            binding.tvVolunteerName.text = foundation.name
            Glide.with(binding.root)
                .load(R.drawable.img_home_helpseeker)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.ivMissionPhoto)
            binding.ivMissionPhoto.setOnClickListener {
                val bundle = bundleOf(MissionDetailFragment.EXTRA_MISSION to foundation)
                itemView.findNavController().navigate(R.id.detailMissionFragment, bundle)
            }
        }
    }

}