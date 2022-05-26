package com.c22ps099.relasiahelpseekerapp.ui.form



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentRequirementsDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RequirementsDialog : BottomSheetDialogFragment() {

    private var binding: FragmentRequirementsDialogBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequirementsDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //TODO: Implement logic
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

}