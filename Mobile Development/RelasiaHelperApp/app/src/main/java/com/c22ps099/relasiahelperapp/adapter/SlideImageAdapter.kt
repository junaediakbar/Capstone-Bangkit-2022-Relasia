package com.c22ps099.relasiahelperapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.databinding.ItemImageSlideBinding

class SlideImageAdapter(private var listImages: List<String>) :
    RecyclerView.Adapter<SlideImageAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemImageSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listImages[position])
    }

    override fun getItemCount() = listImages.size

    class MyViewHolder(private var binding: ItemImageSlideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photoUrl: String) {
            Glide.with(itemView.context)
                .load(photoUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.no_image_placeholder)
                ).placeholder(R.drawable.no_image_placeholder)
                .into(binding.ivPicture)
        }
    }

}