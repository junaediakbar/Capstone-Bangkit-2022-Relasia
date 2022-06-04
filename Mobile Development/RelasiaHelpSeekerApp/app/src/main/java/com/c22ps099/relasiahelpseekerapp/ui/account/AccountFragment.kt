package com.c22ps099.relasiahelpseekerapp.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentAccountBinding
import com.c22ps099.relasiahelpseekerapp.model.Helpseeker
import com.c22ps099.relasiahelpseekerapp.ui.home.PostsViewModel


class AccountFragment : Fragment() {

    private var binding: FragmentAccountBinding? = null

    private var token: String? = ""

    private val viewModel by viewModels<AccountViewModel> {
        AccountViewModel.Factory(getString(R.string.auth, token))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        val genders = resources.getStringArray(R.array.Genders)
        binding?.spProfileGender?.apply {
            val adp = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item, genders
            )
            adapter = adp
            onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        context,
                        getString(R.string.selected_item) + " " +
                                "" + genders[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: Implement Logic
        val helpseeker  = Helpseeker(
            "idjuned",
            "siaapa",
            "soap",
            "sosas"
        )

        binding?.apply {
            btnSave.setOnClickListener{
                viewModel.addNewHelpSeeker(helpseeker)
            }
        }


    }
}
