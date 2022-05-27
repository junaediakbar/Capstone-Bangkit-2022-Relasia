package com.c22ps099.relasiahelpseekerapp.ui.missions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c22ps099.relasiahelpseekerapp.ui.home.HomeFragmentDirections


import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentHomeBinding
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentMissionsBinding


class MissionsFragment : Fragment() {


    private var binding: FragmentMissionsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMissionsBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: Implement Logic
    }

}