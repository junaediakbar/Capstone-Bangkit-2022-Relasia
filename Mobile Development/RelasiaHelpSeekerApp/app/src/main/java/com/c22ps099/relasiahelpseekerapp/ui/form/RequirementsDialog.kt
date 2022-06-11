package com.c22ps099.relasiahelpseekerapp.ui.form



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentRequirementsDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RequirementsDialog : BottomSheetDialogFragment() {

    private var binding: FragmentRequirementsDialogBinding? = null

    private val viewModel by viewModels<FormViewModel> {
        FormViewModel.Factory(getString(R.string.auth, token))
    }

    private var token: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequirementsDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data =  arguments?.getString("req")
        if(data!=""){
            binding?.etRequirement?.setText(data)
        }
        //TODO: Implement logic
        binding?.apply {
            btnPositive.setOnClickListener{
                viewModel.updateRequirements(binding?.etRequirement?.text.toString())
                dismiss()
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

}