package com.c22ps099.relasiahelpseekerapp.ui.bookmarks

import android.app.Application
import android.content.res.Configuration
import android.os.Bundle
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
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.adapter.ListVolunteersAdapter
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentBookmarksBinding
import com.c22ps099.relasiahelpseekerapp.ui.account.VolunteerAccountFragment
import com.c22ps099.relasiahelpseekerapp.ui.account.VolunteerAccountViewModel
import com.c22ps099.relasiahelpseekerapp.ui.login.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class BookmarksFragment : Fragment() {

    private var binding: FragmentBookmarksBinding? = null
    private lateinit var googleAuth: FirebaseAuth

    private val viewModel by viewModels<BookmarksViewModel> {
        BookmarksViewModel.Factory(context?.applicationContext as Application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleAuth = Firebase.auth
        val firebaseUser = googleAuth.currentUser

        if (firebaseUser == null) {
            val navigateAction = BookmarksFragmentDirections
                .actionBookmarkFragmentToLoginFragment()
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
        binding?.apply {
            rvMissions.layoutManager =
                if (activity?.applicationContext?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) GridLayoutManager(
                    context,
                    2
                ) else LinearLayoutManager(context)
        }

        viewModel.apply {
            getBookmarkedVolunteers().observe(viewLifecycleOwner) { volunteer ->
                binding?.apply {
                    rvMissions.adapter = ListVolunteersAdapter("",null,ArrayList(volunteer),).apply {
                        setOnItemClickCallback(object :
                            ListVolunteersAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: VolunteersItem) {
                                val bundle = bundleOf(VolunteerAccountFragment.EXTRA_VOLUNTEER to data)
                                view.findNavController()
                                    .navigate(R.id.volunteerAccountFragment, bundle)
                            }
                        })
                    }
                }
            }
        }
    }

}