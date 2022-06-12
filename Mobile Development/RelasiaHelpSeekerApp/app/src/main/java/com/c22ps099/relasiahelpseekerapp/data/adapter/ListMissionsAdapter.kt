package com.c22ps099.relasiahelpseekerapp.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem

import com.c22ps099.relasiahelpseekerapp.databinding.ItemPostBinding
import com.c22ps099.relasiahelpseekerapp.misc.DateFormatter

class ListMissionsAdapter(private var listMissionResponse: List<MissionItem>) :
    RecyclerView.Adapter<ListMissionsAdapter.MyViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listMissionResponse[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMissionResponse[holder.bindingAdapterPosition]) }
    }

    override fun getItemCount() = listMissionResponse.size

    class MyViewHolder(private var binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mission: MissionItem) {
             val photoUrl = if(mission.featuredImage?.size!=0){
                 mission.featuredImage?.get(0)
             }else{
                 listOf("")
                 mission.featuredImage=  listOf("")
             }
            binding.apply {
                tvVolunteerName.text = mission.title
                tvVolunteerAge.text = DateFormatter.formatDate(mission.startDate) + " - " + DateFormatter.formatDate(mission.endDate)
                tvMissionStatus.text = mission.category
            }
            Glide.with(itemView.context)
                .load(photoUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                ).placeholder(R.drawable.ic_error)
                .into(binding.ivMissionPhoto)
        }
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: MissionItem)
    }

}
