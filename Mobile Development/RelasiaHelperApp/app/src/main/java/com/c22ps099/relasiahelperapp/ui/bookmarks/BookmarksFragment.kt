package com.c22ps099.relasiahelperapp.ui.bookmarks

import android.app.Application
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.adapter.BookmarkedMissionListAdapter
import com.c22ps099.relasiahelperapp.databinding.FragmentBookmarksBinding
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.c22ps099.relasiahelperapp.ui.missionDetail.MissionDetailFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BookmarksFragment : Fragment() {

    private var binding: FragmentBookmarksBinding? = null
    private lateinit var googleAuth: FirebaseAuth

    private val bookmarksViewModel by viewModels<BookmarksViewModel> {
        BookmarksViewModel.Factory(context?.applicationContext as Application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleAuth = Firebase.auth
        val firebaseUser = googleAuth.currentUser

        if (firebaseUser == null) {
            val navigateAction = BookmarksFragmentDirections
                .actionBookmarksFragmentToLoginFragment()
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

        binding?.apply {
            rvMissions.layoutManager =
                if (activity?.applicationContext?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) GridLayoutManager(
                    context,
                    2
                ) else LinearLayoutManager(context)
        }

        bookmarksViewModel.apply {
            getBookmarkedMissions().observe(viewLifecycleOwner) { mission ->
                binding?.apply {
                    rvMissions.adapter = BookmarkedMissionListAdapter(ArrayList(mission)).apply {
                        setOnItemClickCallback(object :
                            BookmarkedMissionListAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: MissionDataItem) {
                                val bundle = bundleOf(MissionDetailFragment.EXTRA_MISSION to data)
                                view.findNavController()
                                    .navigate(R.id.missionDetailFragment, bundle)
                            }
                        })
                    }
                }
            }
        }
    }
}