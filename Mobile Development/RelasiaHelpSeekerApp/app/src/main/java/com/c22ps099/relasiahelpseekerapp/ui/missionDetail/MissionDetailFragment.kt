package com.c22ps099.relasiahelpseekerapp.ui.missionDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.c22ps099.relasiahelpseekerapp.ui.account.AccountViewModel
import com.c22ps099.relasiahelpseekerapp.ui.login.LoginFragment
import com.c22ps099.relasiahelpseekerapp.ui.main.MainActivity
import com.c22ps099.relasiahelpseekerapp.ui.missionEdit.MissionEditFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MissionDetailFragment : Fragment() {

    private var binding: FragmentMissionDetailBinding? = null
    private lateinit var googleAuth: FirebaseAuth

    private var token: String? = ""
    private var phone: String? = ""
    private var name: String? = ""


    private val viewModel by viewModels<MissionDetailViewModel> {
        MissionDetailViewModel.Factory(getString(R.string.auth, token))
    }

    private val accountViewModel by viewModels<AccountViewModel> {
        AccountViewModel.Factory(
            getString(R.string.auth, token),
            FirebaseAuth.getInstance().currentUser?.uid!!
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
                val listImages = mission.featuredImage as List<String?>
                for(i in 0 until listImages.size-1){
                    val storageReference: StorageReference = FirebaseStorage.getInstance()
                        .getReferenceFromUrl(listImages[i]!!)
                    storageReference.delete().addOnSuccessListener {
                        Log.d("storage", "Delete done")
                    }.addOnFailureListener {
                        Log.d("storage", "error while deleting")
                    }
                }
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
        accountViewModel.getHelpseeker(googleAuth.currentUser?.uid!!)
        accountViewModel.helpseeker.observe(viewLifecycleOwner){
            phone = it?.phone
            name = it?.name
        }

        viewModel.volunteers.observe(viewLifecycleOwner){
                binding?.rvVolunteersStatus?.apply {
                    adapter = ListVolunteersAdapter(mission.id!!,viewModel,it)
                    Log.v("ukuran", "${it.size}")
                }
                binding?.apply {
                    tvMissionTitle.text = mission.title
                    tvMissionDate.text =
                        DateFormatter.formatDate(mission.startDate) + " - " + DateFormatter.formatDate(
                            mission.endDate
                        )
                    tvMissionCity.text = mission.city + ", " + mission.province

                    tvMissionReq.text = mission.requirement
                    tvMissionNote.text = mission.note
                    tvMissionCp.text = phone + " (" + name + ")"
                    tvMissionCategory.text = mission.category

                    val acceptedList  = it.filter{it.status=="accepted"}
                    tvApplicant.text = acceptedList.size.toString() + "/" + mission.numberOfNeeds
                    pbApplicant.max = mission.numberOfNeeds?.toInt()!!
                    pbApplicant.progress = acceptedList.size
                    if (acceptedList.size == mission.numberOfNeeds.toInt()) {
                        pbApplicant.progressTintList =
                            ColorStateList.valueOf(resources.getColor(R.color.warm_red))
                    }

                }
        }
        viewModel.apply {
            isSuccess.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { success ->
                    if (success) {
                        Toast.makeText(activity, "Mission Deleted Successfully", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.overridePendingTransition(0, 0)
                    }
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }


    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_MISSION = "extra_mission"
    }
}