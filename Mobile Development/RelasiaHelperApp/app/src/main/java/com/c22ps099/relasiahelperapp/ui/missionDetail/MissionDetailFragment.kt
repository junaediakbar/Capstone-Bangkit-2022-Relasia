package com.c22ps099.relasiahelperapp.ui.missionDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.data.Mission
import com.c22ps099.relasiahelperapp.databinding.FragmentMissionDetailBinding
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.c22ps099.relasiahelperapp.ui.login.LoginFragmentDirections
import com.c22ps099.relasiahelperapp.utils.DateFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MissionDetailFragment : Fragment() {

    companion object {
        const val EXTRA_MISSION = "extra_mission"
    }

    private var binding: FragmentMissionDetailBinding? = null
    private lateinit var googleAuth: FirebaseAuth

    private val missionDetailViewModel by viewModels<MissionDetailViewModel> {
        MissionDetailViewModel.Factory(
            "volunteer.baru",
            arguments?.getParcelable<MissionDataItem>(EXTRA_MISSION) as MissionDataItem
        )
    }

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
                .actionMissionDetailFragmentToLoginFragment()
            findNavController().navigate(navigateAction)

            val mLoginFragment = LoginFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.nav_host_fragment,
                    mLoginFragment,
                    LoginFragment::class.java.simpleName
                )
                setReorderingAllowed(true)
                commit()
            }
        }

        val mission = arguments?.getParcelable<MissionDataItem>(EXTRA_MISSION) as MissionDataItem
        showMissionDetail(mission)
        val missionString = Mission(mission.id)
        binding?.apply {
            fabBack.setOnClickListener {
                val navigateAction = MissionDetailFragmentDirections
                    .actionMissionDetailFragmentToHomeFragment()
                findNavController().navigate(navigateAction)
            }
            btnApply.setOnClickListener {
                applyVolunteerToMission(missionString)
            }
        }
    }

    private fun showMissionDetail(mission: MissionDataItem) {
        binding?.apply {
            ivDetailMission.let {
                Glide.with(requireActivity())
                    .load(mission.featuredImage[0])
                    .into(it)
            }
            tvMissionTitle.text = mission.title
            tvMissionDate.text = DateFormatter.formatDate(mission.startDate) + " - " + DateFormatter.formatDate(mission.endDate)
            tvMissionCity.text = mission.city + ", " + mission.province
            tvApplicant.text = mission.numberOfNeeds
            tvMissionReq.text = mission.requirement
            tvMissionNote.text = mission.note
        }
    }

    private fun applyVolunteerToMission(mission: Mission) {
        missionDetailViewModel.applyMission("volunteer.baru", mission)
    }
}