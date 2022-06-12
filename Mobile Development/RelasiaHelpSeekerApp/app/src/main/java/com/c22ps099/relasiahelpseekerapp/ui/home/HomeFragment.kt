package com.c22ps099.relasiahelpseekerapp.ui.home

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
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
import com.c22ps099.relasiahelpseekerapp.data.adapter.ListFoundationsAdapter
import com.c22ps099.relasiahelpseekerapp.data.adapter.ListMissionsAdapter
import com.c22ps099.relasiahelpseekerapp.data.api.responses.Foundation
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentHomeBinding
import com.c22ps099.relasiahelpseekerapp.misc.visibility
import com.c22ps099.relasiahelpseekerapp.ui.account.AccountFragment
import com.c22ps099.relasiahelpseekerapp.ui.foundationDetail.FoundationDetailFragment
import com.c22ps099.relasiahelpseekerapp.ui.login.LoginFragment
import com.c22ps099.relasiahelpseekerapp.ui.missionDetail.MissionDetailFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var auth: FirebaseAuth
    private var token: String? = ""


    val viewModel by viewModels<HomeViewModel> {
        HomeViewModel.Factory(getString(R.string.auth, token))
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
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            val navigateAction = HomeFragmentDirections
                .actionHomeFragmentToLoginFragment()
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

        if (auth.currentUser != null) viewModel.checkHelpseeker(auth.currentUser?.uid.toString())
        viewModel.isRegistered.observe(viewLifecycleOwner) { success ->
            if (!success) {
                showProfileDialog()
            } else {
                viewModel.getAllMissions(auth.currentUser?.uid!!)
                viewModel.getAllFoundations()
            }
        }

//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w(LoginFragment.TAG, "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//            val token = task.result
//            val msg = getString(R.string.msg_token_fmt, token)
//            Log.d(LoginFragment.TAG, msg)
//            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
//        })

        binding?.apply {
            btnAskHelp.setOnClickListener {
                val navigateAction = HomeFragmentDirections
                    .actionHomeFragmentToFormFragment()
                navigateAction.token = "token"
                findNavController().navigate(navigateAction)
            }
            btnLogout.setOnClickListener {
                auth.signOut()
                val navigateAction = HomeFragmentDirections
                    .actionHomeFragmentToLoginFragment()
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
            }
            rvNearestInstantion.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }

        viewModel.apply {
            missions.observe(viewLifecycleOwner) {
                if(it.isEmpty()){
                    binding?.tvNoMission?.visibility= visibility(true)
                }else{
                    binding?.tvNoMission?.visibility= visibility(false)
                }
                binding?.rvLatestPosts?.apply {
                    adapter = ListMissionsAdapter(it).apply {
                        setOnItemClickCallback(object :
                            ListMissionsAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: MissionItem) {
                                val bundle = bundleOf(MissionDetailFragment.EXTRA_MISSION to data)
                                view.findNavController().navigate(R.id.detailMissionFragment, bundle)
                            }
                        })
                    }
                    Log.v("ukuran", "${it.size}")
                }
            }
            foundations.observe(viewLifecycleOwner) {
                if(it.isEmpty()){
                    binding?.tvNoFoundation?.visibility= visibility(true)
                }else{
                    binding?.tvNoFoundation?.visibility= visibility(false)
                }
                binding?.rvNearestInstantion?.apply {
                    adapter = ListFoundationsAdapter(it).apply {
                        setOnItemClickCallback(object :
                            ListFoundationsAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: Foundation) {
                                val bundle = bundleOf(FoundationDetailFragment.EXTRA_FOUNDATION to data)
                                view.findNavController()
                                    .navigate(R.id.foundationDetailFragment, bundle)
                            }
                        })
                    }
                }
                Log.v("ukuran", "${it.size}")
            }

            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            error.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { message ->
                    showMessage(message)
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

    private fun showProfileDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_fill_profile)
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val btnProfile = dialog.findViewById<Button>(R.id.btn_profile)
        btnProfile.setOnClickListener {
            dialog.dismiss()
            val navigateAction = HomeFragmentDirections
                .actionHomeFragmentToAccountFragment()
            findNavController().navigate(navigateAction)

            val mProfileFragment = AccountFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.container,
                    mProfileFragment,
                    AccountFragment::class.java.simpleName
                )
                addToBackStack(null)
                setReorderingAllowed(true)
                commit()
            }
            dialog.hide()
        }
    }

}