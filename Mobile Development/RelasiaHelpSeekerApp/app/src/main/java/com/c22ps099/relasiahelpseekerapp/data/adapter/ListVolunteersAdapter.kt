package com.c22ps099.relasiahelpseekerapp.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem
import com.c22ps099.relasiahelpseekerapp.databinding.ItemRegisteredVolunteerBinding
import com.c22ps099.relasiahelpseekerapp.ui.account.AccountFragment
import com.c22ps099.relasiahelpseekerapp.ui.account.VolunteerAccountFragment
import com.c22ps099.relasiahelpseekerapp.ui.missionDetail.MissionDetailViewModel

class ListVolunteersAdapter(private var listVolunteersResponse: List<VolunteersItem>) :
    RecyclerView.Adapter<ListVolunteersAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemRegisteredVolunteerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listVolunteersResponse[position])
    }

    override fun getItemCount() = listVolunteersResponse.size

    class MyViewHolder(private var binding: ItemRegisteredVolunteerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(volunteer: VolunteersItem) {
            binding.tvVolunteerName.text = volunteer.name
            binding.ivMissionPhoto.setOnClickListener {
                val bundle = bundleOf(VolunteerAccountFragment.EXTRA_VOLUNTEER to volunteer)
                itemView.findNavController().navigate(R.id.volunteerAccountFragment, bundle)
            }
            binding.apply {
                ibAccept.setOnClickListener{
                }
            }
        }
    }
}