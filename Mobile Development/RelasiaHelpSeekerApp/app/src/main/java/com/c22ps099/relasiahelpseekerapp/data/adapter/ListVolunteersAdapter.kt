package com.c22ps099.relasiahelpseekerapp.data.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem
import com.c22ps099.relasiahelpseekerapp.databinding.ItemRegisteredVolunteerBinding
import com.c22ps099.relasiahelpseekerapp.misc.visibility
import com.c22ps099.relasiahelpseekerapp.ui.missionDetail.MissionDetailViewModel
import java.util.*


class ListVolunteersAdapter(
    var missionId: String,
    private val viewModel: MissionDetailViewModel?,
    private var listVolunteersResponse: List<VolunteersItem>
) :
    RecyclerView.Adapter<ListVolunteersAdapter.MyViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemRegisteredVolunteerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(missionId, itemView, viewModel)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listVolunteersResponse[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                listVolunteersResponse[holder.bindingAdapterPosition]
            )
        }
    }

    override fun getItemCount() = listVolunteersResponse.size

    class MyViewHolder(
        private var missionId: String,
        private var binding: ItemRegisteredVolunteerBinding,
        private var viewModel: MissionDetailViewModel?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(volunteer: VolunteersItem) {
            binding.apply {
                ivMissionPhoto.let {
                    Glide.with(itemView)
                        .load( "${volunteer.picture}").circleCrop()
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error)
                        )
                        .into(it)
                }
                tvVolunteerName.text = volunteer.name
                val year = Calendar.getInstance().get(Calendar.YEAR);

                tvVolunteerAge.text = "${year - (volunteer.birthyear?.toInt() ?: 2022)} Years Old"
            }

            binding.apply {
                tvMissionStatus.text = volunteer.status


                if (volunteer.status == "rejected") {
                    tvMissionStatus.setBackgroundResource(R.drawable.status_border_red);
                    tvMissionStatus.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.warm_red
                        )
                    )
                }
            }

            if (viewModel != null) {

                binding.apply {
                    if (volunteer.status == "pending") {
                        ibAccept.setOnClickListener {
                            viewModel?.editStatusVolunteer(missionId, volunteer.id, "accepted")
                        }
                        ibReject.setOnClickListener {
                            viewModel?.editStatusVolunteer(missionId, volunteer.id, "rejected")
                        }
                    }
                    else{
                        ibAccept.visibility= visibility(false)
                        ibReject.visibility= visibility(false)
                    }

                }
            }else{
                binding.apply {
                    tvMissionStatus.visibility= visibility(false)
                    ibAccept.visibility= visibility(false)
                    ibReject.visibility= visibility(false)
                }
            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: VolunteersItem)
    }
}