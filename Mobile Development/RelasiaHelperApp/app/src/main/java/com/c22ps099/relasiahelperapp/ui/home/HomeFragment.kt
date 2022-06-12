package com.c22ps099.relasiahelperapp.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.adapter.LoadingStateAdapter
import com.c22ps099.relasiahelperapp.adapter.MissionListAdapter
import com.c22ps099.relasiahelperapp.adapter.MissionSearchAdapter
import com.c22ps099.relasiahelperapp.data.MissionRepository
import com.c22ps099.relasiahelperapp.databinding.FragmentHomeBinding
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.c22ps099.relasiahelperapp.ui.missionDetail.MissionDetailFragment
import com.c22ps099.relasiahelperapp.ui.profile.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var auth: FirebaseAuth
    private var isSearch: Boolean = false
    private var uid: String = ""

    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModel.Factory(
            MissionRepository(ApiConfig.getApiService()),
            uid
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            uid = auth.currentUser?.uid.toString()
            homeViewModel.checkVolunteer(auth.currentUser?.uid.toString())
        }

        if (firebaseUser == null) {
            val navigateAction = HomeFragmentDirections
                .actionHomeFragmentToLoginFragment()
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

        homeViewModel.apply {
            isSuccess.observe(viewLifecycleOwner) { success ->
                if (!isSearch && !success) {
                    showProfileDialog()
                } else if (!isSearch && success) {
                    showMissionList()
                }
            }
            listMission.observe(viewLifecycleOwner) { missions ->
                if (isSearch)
                    showMissionSearch(missions)
            }
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            volunteerName.observe(viewLifecycleOwner) {
                setGreetingsName(it)
            }
        }

        binding?.apply {
            svHome.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        isSearch = true
                        binding?.rvMissions?.scrollToPosition(0)
                        binding?.rvMissions?.adapter = null
                        homeViewModel.searchMission(query)
                        svHome.clearFocus()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == "" || newText == null) {
                        isSearch = false
                        binding?.rvMissions?.scrollToPosition(0)
                        binding?.rvMissions?.adapter = null
                        showMissionList()
                    }
                    return false
                }

            })
            btnSettings.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setGreetingsName(volunteerName: String?) {
        binding?.tvGreetings?.text = "Hello, $volunteerName"
    }

    private fun showMissionSearch(missions: List<MissionDataItem?>?) {
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
                    timestamp = mission?.timestamp.toString()
                )
                listMission.add(missionData)
            }
        }
        val adapter = MissionSearchAdapter(listMission)
        binding?.apply {
            rvMissions.setHasFixedSize(true)
            rvMissions.itemAnimator = null
            rvMissions.layoutManager = LinearLayoutManager(requireContext())
            rvMissions.adapter = adapter

            adapter.setOnItemClickCallback(object : MissionSearchAdapter.OnItemClickCallback {
                override fun onItemClicked(data: MissionDataItem) {
                    val bundle = bundleOf(MissionDetailFragment.EXTRA_MISSION to data)
                    findNavController().navigate(R.id.missionDetailFragment, bundle)
                }
            })
        }
        showLoading(false)
    }

    private fun showMissionList() {
        val adapter = MissionListAdapter()
        binding?.apply {
            rvMissions.setHasFixedSize(true)
            rvMissions.itemAnimator = null
            rvMissions.layoutManager = LinearLayoutManager(requireContext())
            rvMissions.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )

            homeViewModel.missions.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }

            buttonRetry.setOnClickListener {
                adapter.retry()
            }

            adapter.addLoadStateListener { loadState ->
                binding.apply {
                    progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                    rvMissions.isVisible = loadState.source.refresh is LoadState.NotLoading
                    buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                    textViewError.isVisible = loadState.source.refresh is LoadState.Error

                    if (loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1
                    ) {
                        rvMissions.isVisible = false
                        textViewEmpty.isVisible = true
                    } else {
                        textViewEmpty.isVisible = false
                    }
                }
            }
        }
    }

    private fun showProfileDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_fill_profile)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val btnProfile = dialog.findViewById<Button>(R.id.btn_profile)
        btnProfile.setOnClickListener {
            dialog.dismiss()
            val navigateAction = HomeFragmentDirections
                .actionHomeFragmentToProfileFragment()
            findNavController().navigate(navigateAction)

            val mProfileFragment = ProfileFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.nav_host_fragment,
                    mProfileFragment,
                    ProfileFragment::class.java.simpleName
                )
                addToBackStack(null)
                setReorderingAllowed(true)
                commit()
            }
            dialog.hide()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}