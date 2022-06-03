package com.c22ps099.relasiahelpseekerapp.ui.home

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.adapter.ListMissionsAdapter
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentHomeBinding
import com.c22ps099.relasiahelpseekerapp.ui.missionDetail.MissionDetailFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var googleAuth: FirebaseAuth

    private var token: String? = ""

    private val viewModel by viewModels<PostsViewModel> {
        PostsViewModel.Factory(getString(R.string.auth, token))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleAuth = Firebase.auth
        val firebaseUser = googleAuth.currentUser

        binding?.apply {
            btnAskHelp.setOnClickListener {
                val navigateAction = HomeFragmentDirections
                    .actionHomeFragmentToFormFragment()
                navigateAction.token = "token"
                findNavController().navigate(navigateAction)
            }
            btnLogout.setOnClickListener{
                googleAuth.signOut()
            }
        }

        val setLayoutManager = if (activity?.applicationContext
                ?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT
        ) {
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        } else {
            GridLayoutManager(context, 2)
        }

        binding?.apply {
            rvLatestPosts.apply {
                setHasFixedSize(true)
                layoutManager = setLayoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL
                    )
                )
            }
        }

        viewModel.apply {
            missions.observe(viewLifecycleOwner) {
                binding?.rvLatestPosts?.apply {
                    adapter = ListMissionsAdapter(it)
                    Log.v("ukuran", "${it.size}")
                }
            }

            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            error.observe(viewLifecycleOwner) {

                it.getContentIfNotHandled()?.let { message ->
                    showMessage(message)
                    viewModel.getAllMissions()
                }

            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}