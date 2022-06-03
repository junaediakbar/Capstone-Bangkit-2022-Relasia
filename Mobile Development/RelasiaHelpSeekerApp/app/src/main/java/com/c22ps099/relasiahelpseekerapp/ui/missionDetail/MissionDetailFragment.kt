package com.c22ps099.relasiahelpseekerapp.ui.missionDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentMissionDetailBinding
import com.c22ps099.relasiahelpseekerapp.ui.login.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MissionDetailFragment : Fragment() {

    companion object {
        const val EXTRA_MISSION = "extra_mission"
    }

    private var binding: FragmentMissionDetailBinding? = null
    private lateinit var googleAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMissionDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleAuth = Firebase.auth
        val firebaseUser = googleAuth.currentUser

        if (firebaseUser == null) {
            val navigateAction = MissionDetailFragmentDirections
                .actionDetailMissionFragmentToLoginFragment()
            findNavController().navigate(navigateAction)

            val mLoginFragment = LoginFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.container,
                    mLoginFragment,
                    LoginFragment::class.java.simpleName
                )
                setReorderingAllowed(true)
                commit()
            }
        }

        val mission = arguments?.getParcelable<MissionItem>(EXTRA_MISSION) as MissionItem
        showMissionDetail(mission)


    }

    @SuppressLint("SetTextI18n")
    private fun showMissionDetail(mission: MissionItem) {
        binding?.apply {
            ivDetailMission.let {
                Glide.with(requireActivity())
                    .load(mission.featuredImage?.get(0))
                    .into(it)
            }
            tvMissionTitle.text = mission.title
            tvMissionDate.text = mission.startDate + " - " + mission.endDate
            tvMissionCity.text = mission.city + ", " + mission.province
            tvApplicant.text = mission.numberOfNeeds
            tvMissionReq.text = mission.requirement
            tvMissionNote.text = mission.note
        }
    }
}