package com.c22ps099.relasiahelpseekerapp.ui.missions

import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.adapter.MissionsFilterAdapter
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentMissionStatusBinding
import com.c22ps099.relasiahelpseekerapp.ui.missionDetail.MissionDetailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MissionStatusFragment : Fragment() {

    private var _binding: FragmentMissionStatusBinding? = null
    private val binding get() = _binding
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String

    private val missionStatusViewModel by viewModels<MissionStatusViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMissionStatusBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) uid = auth.currentUser?.uid.toString()

        if (firebaseUser == null) {

        }

        when (arguments?.getInt(ARG_SECTION_NUMBER, 0)) {
            0 -> missionStatusViewModel.filterMission(uid,"active")
            1 -> missionStatusViewModel.filterMission(uid,"inactive")
        }

        missionStatusViewModel.apply {

            isLoading.observe(requireActivity()) {
                showLoading(it)
            }
            status.observe(requireActivity()){
                listMission.observe(requireActivity()) { missions ->
                    showMissionFilter(missions,it)
                }
            }
        }
    }

    private fun showMissionFilter(missions: List<MissionItem?>?,status : String) {
        showLoading(true)
        val listMission = ArrayList<MissionItem>()
        if (missions != null) {
            for (mission in missions) {
                val missionData = MissionItem(
                    endDate = mission?.endDate.toString(),
                    city = mission?.city.toString(),
                    title = mission?.title.toString(),
                    featuredImage = mission?.featuredImage,
                    numberOfNeeds = mission?.numberOfNeeds.toString(),
                    province = mission?.province.toString(),
                    category = mission?.category.toString(),
                    startDate = mission?.startDate.toString(),
                    address = mission?.title.toString(),
                    id = mission?.id.toString(),
                    helpseeker = mission?.helpseeker.toString(),
                    note = mission?.note.toString(),
                    requirement = mission?.requirement.toString(),
                    timestamp = mission?.timestamp.toString(),
                )
                listMission.add(missionData)
            }
        }
        val adapter = MissionsFilterAdapter(listMission,status)
        binding?.apply {
            rvMissions.itemAnimator = null
            rvMissions.layoutManager = LinearLayoutManager(requireContext())
            rvMissions.adapter = adapter

            adapter.setOnItemClickCallback(object : MissionsFilterAdapter.OnItemClickCallback {
                override fun onItemClicked(data: MissionItem) {
                    val bundle = bundleOf(MissionDetailFragment.EXTRA_MISSION to data)
                    findNavController().navigate(R.id.detailMissionFragment, bundle)
                }
            })
        }
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        binding?.root?.requestLayout()
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}