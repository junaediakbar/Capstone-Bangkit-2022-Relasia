package com.c22ps099.relasiahelpseekerapp.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentNotesDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment





class NotesDialog : BottomSheetDialogFragment() {

    private var binding: FragmentNotesDialogBinding? = null

    private val viewModel by viewModels<FormViewModel> {
        FormViewModel.Factory(getString(R.string.auth, token))
    }

    private var token: String? = ""

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
        val data =  arguments?.getString("notes")
        if(data!=""){
            binding?.etNotes?.setText(data)
        }
        //TODO: Implement logic
        binding?.apply {
            btnPositive.setOnClickListener{
                viewModel.updateNotes(binding?.etNotes?.text.toString())
                dismiss()
            }
        }
    }
    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}