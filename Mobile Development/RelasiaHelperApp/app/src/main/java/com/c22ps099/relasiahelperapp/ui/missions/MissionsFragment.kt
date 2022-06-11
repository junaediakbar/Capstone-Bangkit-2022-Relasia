package com.c22ps099.relasiahelperapp.ui.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.adapter.AppliedMissionListAdapter
import com.c22ps099.relasiahelperapp.adapter.LoadingStateAdapter
import com.c22ps099.relasiahelperapp.adapter.MissionFilterAdapter
import com.c22ps099.relasiahelperapp.data.MissionRepository
import com.c22ps099.relasiahelperapp.databinding.FragmentMissionsBinding
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.c22ps099.relasiahelperapp.ui.missionDetail.MissionDetailFragment
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class MissionsFragment : Fragment() {

    private var binding: FragmentMissionsBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    private var isFilter: Boolean = false
    private var statusSelected: String = ""

    private val missionsViewModel by viewModels<MissionsViewModel> {
        MissionsViewModel.Factory(
            MissionRepository(ApiConfig.getApiService()),
            uid
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMissionsBinding.inflate(
            inflater, container, false
        )
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

        val viewPager = binding?.viewPager
        viewPager?.adapter = SectionsPagerAdapter(activity as AppCompatActivity, "Test User")
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> viewPager?.setBackgroundColor(Color.DKGRAY)
            Configuration.UI_MODE_NIGHT_NO -> viewPager?.setBackgroundColor(Color.LTGRAY)
            else -> viewPager?.setBackgroundColor(Color.TRANSPARENT)
        }

        val tabs = binding?.tabs
        tabs?.setSelectedTabIndicatorColor(Color.parseColor("#3587A4"))
        if (tabs != null && viewPager != null) {
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        missionsViewModel.apply {
            isSuccess.observe(viewLifecycleOwner) { success ->
                isFilter = !success
            }
            listMission.observe(viewLifecycleOwner) { missions ->
                showMissionFilter(missions)
            }
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }

        if (!isFilter) {
            showMissionStatusList()
        }

        binding?.apply {
            chipsGroup.setOnCheckedChangeListener { chipGroup, checkedId ->
                val titleOrNull = chipGroup.findViewById<Chip>(checkedId)?.text
                statusSelected = titleOrNull.toString().lowercase(Locale.getDefault())
                if (titleOrNull != null && titleOrNull != "") {
                    isFilter = true
                    binding?.rvMissions?.scrollToPosition(0)
                    missionsViewModel.filterMission(statusSelected)
                } else {
                    isFilter = false
                    binding?.rvMissions?.scrollToPosition(0)
                    showMissionStatusList()
                }
                Toast.makeText(
                    chipGroup.context,
                    titleOrNull ?: "Display All Applied Missions",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showMissionStatusList() {
        val adapter = AppliedMissionListAdapter(uid)
        binding?.apply {
            rvMissions.setHasFixedSize(true)
            rvMissions.itemAnimator = null
            rvMissions.layoutManager = LinearLayoutManager(requireContext())
            rvMissions.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
            missionsViewModel.missionsStatus.observe(viewLifecycleOwner) {
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
            rvMissions.setHasFixedSize(true)
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
}