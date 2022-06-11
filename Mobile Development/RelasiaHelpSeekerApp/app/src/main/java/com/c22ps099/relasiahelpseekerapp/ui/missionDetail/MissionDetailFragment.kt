package com.c22ps099.relasiahelpseekerapp.ui.missionDetail

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.adapter.ListVolunteersAdapter
import com.c22ps099.relasiahelpseekerapp.data.adapter.SlideImageAdapter
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentMissionDetailBinding
import com.c22ps099.relasiahelpseekerapp.misc.DateFormatter
import com.c22ps099.relasiahelpseekerapp.ui.login.LoginFragment
import com.c22ps099.relasiahelpseekerapp.ui.missionEdit.MissionEditFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MissionDetailFragment : Fragment() {

    private var binding: FragmentMissionDetailBinding? = null
    private lateinit var googleAuth: FirebaseAuth

    private var token: String? = ""

    private val viewModel by viewModels<MissionDetailViewModel> {
        MissionDetailViewModel.Factory(getString(R.string.auth, token))
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
        viewModel.getVolunteersbyMissions(mission.id)
        binding?.apply {
            fabBack.setOnClickListener {
                val navigateAction = MissionDetailFragmentDirections
                    .actionDetailMissionFragmentToHomeFragment()
                findNavController().navigate(navigateAction)
            }
            btnDelete.setOnClickListener {
                viewModel.deleteMission(mission.id)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showMissionDetail(mission: MissionItem) {
        binding?.apply {

            val photoUrl = if(mission.featuredImage?.size!=0){
                    mission.featuredImage?.get(0)
            }else{
                    listOf("")
                    mission.featuredImage=  listOf("")
            }
            rvImages.apply {
                setHasFixedSize(true)
                layoutManager=LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter=SlideImageAdapter(mission.featuredImage as List<String>)
            }

            var snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(rvImages)

            tvMissionTitle.text = mission.title
            tvMissionDate.text = DateFormatter.formatDate(mission.startDate) + " - " + DateFormatter.formatDate(mission.endDate)
            tvMissionCity.text = mission.city + ", " + mission.province
            tvMissionReq.text = mission.requirement
            tvMissionNote.text = mission.note
            ibEdit.setOnClickListener{
                val bundle = bundleOf(MissionEditFragment.EXTRA_MISSION to mission)
                view?.findNavController()?.navigate(R.id.missionEditFragment, bundle)
            }
        }

        binding?.apply {
            val setLayoutManager = if (activity?.applicationContext
                    ?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT
            ) {
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            } else {
                GridLayoutManager(context, 2)
            }
            rvVolunteersStatus.apply {
                setHasFixedSize(true)
                layoutManager = setLayoutManager
            }
        }

        viewModel.volunteers.observe(viewLifecycleOwner){
                binding?.rvVolunteersStatus?.apply {
                    adapter = ListVolunteersAdapter(mission.id!!,viewModel,it)
                    Log.v("ukuran", "${it.size}")
                }
                binding?.apply {
                    val acceptedList  = it.filter{it.status=="accepted"}
                    pbApplicant.max = mission.numberOfNeeds?.toInt()!!
                    pbApplicant.progress = acceptedList.size
                    if (acceptedList.size == mission.numberOfNeeds.toInt()) {
                        pbApplicant.progressTintList =
                            ColorStateList.valueOf(resources.getColor(R.color.warm_red))
                    }

                }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_MISSION = "extra_mission"
    }
}