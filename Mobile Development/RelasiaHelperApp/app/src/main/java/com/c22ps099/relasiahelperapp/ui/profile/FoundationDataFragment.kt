package com.c22ps099.relasiahelperapp.ui.profile

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.data.Foundation
import com.c22ps099.relasiahelperapp.databinding.FragmentFoundationDataBinding
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.FoundationDataItem
import com.c22ps099.relasiahelperapp.network.responses.FoundationResponse
import com.c22ps099.relasiahelperapp.ui.main.BaseActivity
import com.c22ps099.relasiahelperapp.utils.timeStampDialog
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoundationDataFragment : Fragment() {

    private var binding: FragmentFoundationDataBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    private var idFoundation: String = ""
    private lateinit var loading: ProgressDialog
    private var positionSelected: Int = 0
    private lateinit var foundationList: List<FoundationDataItem>

    private val foundationDataViewModel by viewModels<FoundationDataViewModel> {
        FoundationDataViewModel.Factory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoundationDataBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) uid = auth.currentUser?.uid.toString()

        foundationDataViewModel.apply {
            isSuccess.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { success ->
                    if (success) {
                        showSuccessDialog()
                    }
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                binding?.btnSave?.isEnabled = !it
                showLoading(it)
            }
        }

        initSpinnerFoundation()

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
                        positionSelected = position
                        for (i in foundationList.indices) {
                            if (i == positionSelected) {
                                idFoundation = foundationList[i].id
                                binding?.etFoundationLoc?.setText(foundationList[i].address)
                            }
                        }
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
            btnSave.setOnClickListener {
                registerFoundation(idFoundation)
            }
        }
    }

    private fun registerFoundation(idFoundation: String) {
        foundationDataViewModel.registFoundation(uid, Foundation(idFoundation))
    }

    @SuppressLint("SetTextI18n")
    private fun showSuccessDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val btnHome = dialog.findViewById<Button>(R.id.btn_home)
        val tvTime = dialog.findViewById<TextView>(R.id.tv_dialog_time)
        val tvId = dialog.findViewById<TextView>(R.id.tv_success_id)
        val tvDesc = dialog.findViewById<TextView>(R.id.tv_dialog_desc)
        tvTime.text = timeStampDialog
        tvId.text = "ID Foundation :\n${idFoundation}"
        tvDesc.text = "You have been registered to a foundation. Please wait until you verified by the foundation"
        btnHome.setOnClickListener {
            dialog.dismiss()
            activity?.finish()
            activity?.overridePendingTransition(0, 0)
            val intent = Intent(activity, BaseActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(0, 0)
            dialog.hide()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun initSpinnerFoundation() {
        loading = ProgressDialog.show(context, null, "Please Wait...", true, false)

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
                            foundationList = foundationItems
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
}