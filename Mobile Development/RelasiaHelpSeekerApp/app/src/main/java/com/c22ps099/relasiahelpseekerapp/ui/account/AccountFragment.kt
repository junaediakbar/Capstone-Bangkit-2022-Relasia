package com.c22ps099.relasiahelpseekerapp.ui.account

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.HelpseekerDataResponse
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentAccountBinding
import com.c22ps099.relasiahelpseekerapp.misc.*
import com.c22ps099.relasiahelpseekerapp.model.Helpseeker
import com.c22ps099.relasiahelpseekerapp.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.*


class AccountFragment : Fragment() {

    private var binding: FragmentAccountBinding? = null

    private var token: String? = ""

    private lateinit var auth: FirebaseAuth
    private var getFile: File? = null
    private var avatarLink: String = ""
    private var avatarLinkOld: String = ""


    private var isRegisteredUser: Boolean = false

    private var provinceSelected: String = ""
    private var citySelected: String = ""

    private val viewModel by viewModels<AccountViewModel> {
        AccountViewModel.Factory(
            getString(R.string.auth, token),
            FirebaseAuth.getInstance().currentUser?.uid!!
        )
    }

    val viewModelUser by viewModels<HomeViewModel> {
        HomeViewModel.Factory(getString(R.string.auth, token))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: Implement Logic
        auth = FirebaseAuth.getInstance()

        viewModel.apply {
            getHelpseeker(auth.currentUser?.uid!!)
            helpseeker.observe(viewLifecycleOwner) {
                setDataHelpseeker(it)
                avatarLink = it?.picture.toString()
                isRegisteredUser = true
                avatarLinkOld = it?.picture.toString()
            }
            isSuccess.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { success ->
                    if (success) {
                        showSuccessDialog(requireContext())
                    } else {
                        Toast.makeText(activity, "Error when upload form", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

        }

        if (!allPermissionGranted()) {
            launcherPermissionRequest.launch(REQUIRED_PERMISSIONS.toTypedArray())
        }

        binding?.apply {
            btnSave.setOnClickListener {
                if (getFile != null) {
                    uploadToFirebaseStorage(getFile)
                } else if (
                    getFile == null && avatarLink != ""
                ) {
                    trySubmit()
                } else {
                    Toast.makeText(activity, "Please input an image!", Toast.LENGTH_SHORT).show()
                }
            }
            ibAddPhoto.setOnClickListener {
                startGallery()
            }
            tvProfileEmail.text = auth.currentUser?.email
        }
        setDropDown()
    }

    private fun uploadToFirebaseStorage(fileImage: File?) {
        val file = reduceFileImage(fileImage)
        var link: String
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("images/profile/${fileName}")
        val uploadTask = ref.putFile(Uri.fromFile(file))
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                link = task.result.toString()
                avatarLink = link
                if (avatarLink != "") {
                    trySubmit()
                }
                Log.v("Link", "===>$link")
                Toast.makeText(activity, link, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(activity, "Error when upload files", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())
            getFile = myFile
            binding?.ivProfile?.let {
                Glide.with(binding?.ivProfile?.context!!)
                    .load(selectedImg).circleCrop()
                    .placeholder(R.drawable.ic_error)
                    .into(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private val launcherPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (!allPermissionGranted()) {
            binding?.root?.let {
                showSnackbar(it, getString(R.string.permission_denied))
            }
            findNavController().navigateUp()
        }
    }

    fun setDataHelpseeker(helpseeker: HelpseekerDataResponse?) {
        binding?.apply {
            etProfileName.setText(helpseeker?.name)
            tvProfileEmail.setText(auth.currentUser?.email)
            etProfilePhone.setText(helpseeker?.phone)
            spFormProvince.setSelection(itemsProv.indexOf("${helpseeker?.province}"))
            val listOfKabs = itemsKab("${helpseeker?.province}")

            spFormCity.item = itemsKab("${helpseeker?.province}").toMutableList() as List<Any>
            spFormCity.setSelection(listOfKabs.indexOf("${helpseeker?.city}"))

            ivProfile.let {
                Glide.with(ivProfile.context)
                    .load(helpseeker?.picture).circleCrop()
                    .placeholder(R.drawable.ic_error)
                    .into(it)
            }
        }
    }

    private fun setDropDown() {
        binding?.apply {
            spFormProvince.apply {
                val provinces = itemsProv
                item = provinces.toMutableList() as List<Any>?
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?, position: Int, id: Long
                    ) {
                        provinceSelected = provinces[position]
                        viewModel.updateProvince(provinces[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        errorText = "On Nothing Selected"
                    }
                }
            }

            viewModel.province.observe(viewLifecycleOwner) {
                spFormCity.apply {
                    val cities = itemsKab(it)
                    item = cities.toMutableList() as List<Any>?
                    onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View?, position: Int, id: Long
                        ) {
                            viewModel.updateCity(cities[position])
                            citySelected = cities[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            errorText = "On Nothing Selected"
                        }
                    }
                }
            }

        }
    }

    private fun trySubmit() {
        Log.e("id", "===>>>$id")
        val email = auth.currentUser?.email
        val phone = binding?.etProfilePhone?.text.toString()
        var helpseeker = Helpseeker(
            avatarLink,
            "${citySelected}",
            "${provinceSelected}",
            "${binding?.etProfilePhone?.text}",
            "${binding?.etProfileName?.text}",
            "${auth.currentUser?.uid}"
        )

        if (isRegisteredUser) {
            if(avatarLinkOld!=avatarLink){
                val storageReference: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(avatarLinkOld) //urifinal is a String variable with the url
                storageReference.delete().addOnSuccessListener {
                    Log.d("storage", "Delete done")
                }.addOnFailureListener {
                    Log.d("storage", "error while deleting")
                }
            }
            viewModel.updateHelpseeker(helpseeker)
        } else {
            viewModel.addNewHelpSeeker(helpseeker)
        }


    }

    private fun allPermissionGranted() = AccountFragment.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            activity?.baseContext as Context,
            it as String
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun showSuccessDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_fill_profile)
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val btnProfile = dialog.findViewById<Button>(R.id.btn_profile)
        btnProfile.setOnClickListener {
            dialog.dismiss()
            val navigateAction = AccountFragmentDirections
                .actionAccountFragmentToHomeFragment()
            findNavController().navigate(navigateAction)
            dialog.hide()
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            mutableListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            mutableListOf()
        }
        const val EXTRA_LOC = "extra_location"
        const val EXTRA_TOKEN = "extra_token"
    }
}
