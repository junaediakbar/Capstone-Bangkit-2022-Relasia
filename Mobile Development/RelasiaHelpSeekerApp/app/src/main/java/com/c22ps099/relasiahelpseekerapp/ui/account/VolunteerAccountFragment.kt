package com.c22ps099.relasiahelpseekerapp.ui.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentVolunteerAccountBinding
import com.c22ps099.relasiahelpseekerapp.ui.missionDetail.MissionDetailFragment

class VolunteerAccountFragment : Fragment() {


    private var binding: FragmentVolunteerAccountBinding? = null

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
    }

    @SuppressLint("SetTextI18n")
    private fun showVolunteerData(volunteer: VolunteersItem) {
        binding?.apply {
            etProfileAddress.setText(volunteer.address)
            etProfileBirthdate.setText(volunteer.birthyear)
            etProfileName.setText(volunteer.name)
            etProfileCity.setText(volunteer.city)
            etProfilePhone.setText(volunteer.phone)
            etProfileProvince.setText("Provinsi")
        }
    }

    companion object {
        const val EXTRA_VOLUNTEER = "extra_volunteer"
    }
}