package com.c22ps099.relasiahelperapp.ui.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.adapter.MissionFilterAdapter
import com.c22ps099.relasiahelperapp.databinding.FragmentMissionStatusBinding
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.c22ps099.relasiahelperapp.ui.missionDetail.MissionDetailFragment
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
            val navigateAction = MissionsFragmentDirections
                .actionMissionsFragmentToLoginFragment()
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

        when (arguments?.getInt(ARG_SECTION_NUMBER, 0)) {
            0 -> missionStatusViewModel.filterMissionPending(uid)
            1 -> missionStatusViewModel.filterMissionRejected(uid)
            else -> missionStatusViewModel.filterMissionAccepted(uid)
        }

        missionStatusViewModel.apply {
            listMission.observe(requireActivity()) { missions ->
                showMissionFilter(missions)
            }
            isLoading.observe(requireActivity()) {
                showLoading(it)
            }
        }
    }

    private fun showMissionFilter(missions: List<MissionDataItem?>?) {
        showLoading(true)
        val listMission = ArrayList<MissionDataItem>()
        if (missions != null) {
            for (mission in missions) {
                val missionData = MissionDataItem(
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
                    volunteers = mission?.volunteers?.toList() ?: listOf()
                )
                listMission.add(missionData)
            }
        }
        val adapter = MissionFilterAdapter(listMission, uid)
        binding?.apply {
            rvMissions.itemAnimator = null
            rvMissions.layoutManager = LinearLayoutManager(requireContext())
            rvMissions.adapter = adapter

            adapter.setOnItemClickCallback(object : MissionFilterAdapter.OnItemClickCallback {
                override fun onItemClicked(data: MissionDataItem) {
                    val bundle = bundleOf(MissionDetailFragment.EXTRA_MISSION to data)
                    findNavController().navigate(R.id.missionDetailFragment, bundle)
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