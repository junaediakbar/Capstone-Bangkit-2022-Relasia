package com.c22ps099.relasiahelperapp.ui.missions

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.adapter.AppliedMissionListAdapter
import com.c22ps099.relasiahelperapp.adapter.LoadingStateAdapter
import com.c22ps099.relasiahelperapp.data.MissionRepository
import com.c22ps099.relasiahelperapp.databinding.FragmentMissionsBinding
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MissionsFragment : Fragment() {

    private var binding: FragmentMissionsBinding? = null
    private lateinit var googleAuth: FirebaseAuth
    private lateinit var emailAuth: FirebaseAuth
    private lateinit var uid: String

    private val missionsViewModel by viewModels<MissionsViewModel> {
        MissionsViewModel.Factory(
            MissionRepository(ApiConfig.getApiService()),
            uid,
            activity?.applicationContext as Application
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

        googleAuth = Firebase.auth
        val firebaseUser = googleAuth.currentUser

        emailAuth = FirebaseAuth.getInstance()
        if (emailAuth.currentUser != null) uid = emailAuth.currentUser?.uid.toString()

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
}