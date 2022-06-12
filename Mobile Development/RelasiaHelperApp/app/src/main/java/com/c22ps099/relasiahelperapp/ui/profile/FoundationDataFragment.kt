package com.c22ps099.relasiahelperapp.ui.profile

import android.app.Application
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.databinding.FragmentFoundationDataBinding
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.FoundationDataItem
import com.c22ps099.relasiahelperapp.network.responses.FoundationResponse
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoundationDataFragment : Fragment() {

    private var binding: FragmentFoundationDataBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    private lateinit var idFoundation: String
    private lateinit var loading: ProgressDialog

    private val foundationDataViewModel by viewModels<FoundationDataViewModel> {
        FoundationDataViewModel.Factory(
            arguments?.getParcelable<FoundationDataItem>(EXTRA_VOLUNTEER) as FoundationDataItem,
            activity?.applicationContext as Application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_foundation_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) uid = auth.currentUser?.uid.toString()

        loading = ProgressDialog.show(context, null, "harap tunggu...", true, false)

        ApiConfig.getApiService().getFoundation()
            .enqueue(object : Callback<FoundationResponse> {
                override fun onResponse(
                    call: Call<FoundationResponse>,
                    response: Response<FoundationResponse>
                ) {
                    if (response.isSuccessful) {
                        loading.dismiss()
                        val foundationItems = response.body()?.data
                        val listSpinner: MutableList<String> = ArrayList()
                        if (foundationItems != null) {
                            for (i in foundationItems.indices) {
                                listSpinner.add(foundationItems[i].name)
                            }
                        }
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            listSpinner
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding?.spFoundation?.adapter = adapter
                    } else {
                        loading.dismiss()
                        Toast.makeText(context, "Fail to load foundation data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<FoundationResponse>, t: Throwable) {
                    loading.dismiss()
                    Log.e("TAG", "onFailure: ${t.message.toString()}")
                    Toast.makeText(context, "Connection Internet Trouble", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        binding?.apply {
            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
            spFoundation.apply {
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        val selectedName = parent.getItemAtPosition(position).toString()
                        //                requestDetailDosen(selectedName);
                        Toast.makeText(
                            context,
                            "You choose foundation $selectedName",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }
        }
    }

    private fun initSpinnerFoundation() {
        loading = ProgressDialog.show(context, null, "harap tunggu...", true, false)

        ApiConfig.getApiService().getFoundation()
            .enqueue(object : Callback<FoundationResponse> {
                override fun onResponse(
                    call: Call<FoundationResponse>,
                    response: Response<FoundationResponse>
                ) {
                    if (response.isSuccessful) {
                        loading.dismiss()
                        val foundationItems = response.body()?.data
                        val listSpinner: MutableList<String> = ArrayList()
                        if (foundationItems != null) {
                            for (i in foundationItems.indices) {
                                listSpinner.add(foundationItems[i].name)
                            }
                        }
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            listSpinner
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding?.spFoundation?.adapter = adapter
                    } else {
                        loading.dismiss()
                        Toast.makeText(context, "Fail to load foundation data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<FoundationResponse>, t: Throwable) {
                    loading.dismiss()
                    Log.e("TAG", "onFailure: ${t.message.toString()}")
                    Toast.makeText(context, "Connection Internet Trouble", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    companion object {
        const val EXTRA_VOLUNTEER = "extra_volunteer"
    }
}