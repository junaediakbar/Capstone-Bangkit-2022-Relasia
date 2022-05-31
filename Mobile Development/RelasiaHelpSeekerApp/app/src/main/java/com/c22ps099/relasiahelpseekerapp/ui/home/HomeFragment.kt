package com.c22ps099.relasiahelpseekerapp.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.adapter.ListMissionsAdapter
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionsResponseItem
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentHomeBinding
import com.c22ps099.relasiahelpseekerapp.databinding.ItemPostBinding
import com.c22ps099.relasiahelpseekerapp.utils.visibility

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

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

//        val layoutManager = if (activity?.applicationContext
//                ?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT
//        ) {
//            LinearLayoutManager(requireContext())
//        } else {
//            GridLayoutManager(requireContext(), 2)
//        }

        viewModel.apply {
            missions.observe(viewLifecycleOwner) {
                val listMissionsAdapter = ListMissionsAdapter(ArrayList(it))
                binding?.apply {
                    rvLatestPosts.apply {
                        adapter = listMissionsAdapter
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                    }
                }
            }
        }

        binding?.apply {
            btnAskHelp.setOnClickListener {
                val navigateAction = HomeFragmentDirections
                    .actionHomeFragmentToFormFragment()
                navigateAction.token = "token"
                findNavController().navigate(navigateAction)
            }
        }
    }

}