package com.c22ps099.relasiahelpseekerapp.ui.form

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentFormBinding





class FormFragment : Fragment() {

    private var binding: FragmentFormBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(inflater, container, false)

        return binding?.root

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            btnPersyaratan.setOnClickListener {
                val bottomSheet = RequirementsDialog()
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
            btnNotes.setOnClickListener {
                val bottomSheet = NotesDialog()
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }

            etFormLocation.setOnClickListener{
                val bottomSheet = MapsDialog()
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
        }
    }


}