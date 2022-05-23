package com.c22ps099.relasiahelpseekerapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentHomeBinding
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentLoginBinding
import com.c22ps099.relasiahelpseekerapp.ui.login.LoginFragmentDirections


class HomeFragment : Fragment() {


    private var binding: FragmentHomeBinding? = null

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