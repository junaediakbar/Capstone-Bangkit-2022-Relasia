package com.c22ps099.relasiahelpseekerapp.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionsResponseItem
import com.c22ps099.relasiahelpseekerapp.databinding.ItemPostBinding

class ListMissionsAdapter (var listMissionResponse: List<MissionsResponseItem>):
    RecyclerView.Adapter<ListMissionsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listMissionResponse[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMissionResponse[position]) }

    }


    override fun getItemCount() = listMissionResponse.size

    class MyViewHolder(private var binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mission: MissionsResponseItem) {
            binding.tvVolunteerName.text = mission.title
            Glide.with(binding.root)
                .load(mission.featuredImage[0])
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.ivMissionPhoto)

        }
    }
    interface OnItemClickCallback {
        fun onItemClicked(mission: MissionsResponseItem)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}
