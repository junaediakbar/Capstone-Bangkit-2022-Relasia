package com.c22ps099.relasiahelpseekerapp.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentNotesDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment





class NotesDialog : BottomSheetDialogFragment() {

    private var binding: FragmentNotesDialogBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            btnPositive.setOnClickListener{

            }
        }
    }
    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}