package com.c22ps099.relasiahelpseekerapp.ui.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentAccountBinding
import com.c22ps099.relasiahelpseekerapp.model.Helpseeker
import com.google.firebase.auth.FirebaseAuth


class AccountFragment : Fragment() {

    private var binding: FragmentAccountBinding? = null

    private var token: String? = ""



    private lateinit var auth: FirebaseAuth

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
        binding?.spProfileGender?.item = genders.toMutableList() as List<Any>?
        binding?.spProfileGender?.apply {
            onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?, position: Int, id: Long
                ) {
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

        binding?.apply {
            btnSave.setOnClickListener{
                trySubmit()
            }
        }
    }

    private fun trySubmit(){
        auth = FirebaseAuth.getInstance()
        val id = auth.currentUser?.uid
        Log.e("id","===>>>$id")
        val  email =auth.currentUser?.email
        val phone = binding?.etProfilePhone?.text.toString()
        var helpseeker  = Helpseeker(
            "${binding?.etProfileName?.text}",
            "${binding?.etProfilePhone?.text}",
            "${binding?.etProfilePhone?.text}",
            "$id"
        )
        viewModel.addNewHelpSeeker(helpseeker)
    }

    companion object {
        const val EXTRA_VOLUNTEER = "extra_volunteer"
    }
}
