package com.c22ps099.relasiahelpseekerapp.data.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionsResponseItem
import com.c22ps099.relasiahelpseekerapp.databinding.ItemPostBinding
import com.c22ps099.relasiahelpseekerapp.ui.home.HomeFragmentDirections

class ListMissionsAdapter(private val listUser:ArrayList<MissionsResponseItem>) : RecyclerView.Adapter<ListMissionsAdapter.ListViewHolder>() {

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPostBinding.bind(itemView)

        fun bind(mission: MissionsResponseItem){
            Glide.with(itemView.context)
                .load(mission.featuredImage[0])
                .into(binding.ivMissionPhoto)
            binding.tvVolunteerName.text = StringBuilder("@").append(mission.title)
            binding.tvVolunteerPlace.text = mission.city

            Log.v("photoUrl","ini urlnya ${mission.featuredImage[0]}")
//            binding.btnInformation.setOnClickListener {
//                val intent = Intent(itemView.context, UserDetails::class.java)
//                intent.putExtra(UserDetails.USER, user)
//                itemView.context.startActivity(intent)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMissionsAdapter.ListViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListMissionsAdapter.ListViewHolder, position: Int) {
        holder.bind(listUser[position])

    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}