package com.c22ps099.relasiahelpseekerapp.ui.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentVolunteerAccountBinding
import com.c22ps099.relasiahelpseekerapp.misc.visibility
import java.util.*

class VolunteerAccountFragment : Fragment() {


    private var binding: FragmentVolunteerAccountBinding? = null

    private val viewModel by viewModels<VolunteerAccountViewModel> {
        VolunteerAccountViewModel.Factory(
            arguments?.getParcelable<VolunteersItem>(EXTRA_VOLUNTEER)?.id?: "",
            activity?.application!!
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVolunteerAccountBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: Implement Logic
        val volunteer = arguments?.getParcelable<VolunteersItem>(EXTRA_VOLUNTEER) as VolunteersItem
        showVolunteerData(volunteer)
         viewModel.apply {
           isFavorite.observe(viewLifecycleOwner) {
               binding?.apply {
                   btnAccBookmark.visibility = visibility(!it)
                   btnAccUnbookmark.visibility = visibility(it)
               }
           }
         }

        binding?.apply {
            btnAccBookmark.setOnClickListener {
                viewModel.setFavorite(volunteer, true)
                Toast.makeText(activity, getString(R.string.success_add_favorite), Toast.LENGTH_SHORT).show()
            }
            btnAccUnbookmark.setOnClickListener {
                viewModel.setFavorite(volunteer, false)
                Toast.makeText(activity, getString(R.string.removed_from_favorite), Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showVolunteerData(volunteer: VolunteersItem) {
        binding?.apply {
            imgAccProfile.let {
                Glide.with(imgAccProfile.context)
                    .load( "${volunteer.picture}").circleCrop()
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgAccProfile)
            }

            val year = Calendar.getInstance().get(Calendar.YEAR);
            tvAccBirthyear.text = "${year - (volunteer.birthyear?.toInt() ?: 2022)} Years Old"

            tvAccName.text = volunteer.name
            tvAccAddress.text=volunteer.address
            tvAccMission.text="${volunteer.missions?.size} Missions Applied"
            tvAccPhone.text= volunteer.phone
            tvAccLocation.text ="${volunteer.city}, ${volunteer.province}"
            if (volunteer.gender=="female"){
                tvAccName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_woman, 0);
            }
        }
    }

    companion object {
        const val EXTRA_VOLUNTEER = "extra_volunteer"
    }
}