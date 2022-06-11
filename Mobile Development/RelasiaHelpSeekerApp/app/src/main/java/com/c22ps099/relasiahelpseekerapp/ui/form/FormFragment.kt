package com.c22ps099.relasiahelpseekerapp.ui.form

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentFormBinding
import com.c22ps099.relasiahelpseekerapp.misc.*
import com.c22ps099.relasiahelpseekerapp.model.Mission
import com.c22ps099.relasiahelpseekerapp.view.setDate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import java.util.*


class FormFragment : Fragment() {
    private val viewModel by viewModels<FormViewModel> {
        FormViewModel.Factory(getString(R.string.auth, token))
    }

    private var token: String? = ""
    private var timeNow: String? = ""
    private var category: String? = ""
    private var citySelected: String? = ""
    private var provinceSelected: String? = ""
    private var imagesUri = mutableListOf<Uri>()
    private var imagesLink = mutableListOf<String?>()

    private var requirements : String? =""
    private var notes : String? =""

    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private var binding: FragmentFormBinding? = null
    private lateinit var googleAuth: FirebaseAuth

    private var uploadError: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().currentBackStackEntry?.savedStateHandle?.apply {
            getLiveData<String>("loc").observe(viewLifecycleOwner) { address ->
                binding?.etFormLocation?.setText(address)
            }
        }

        if (!allPermissionGranted()) {
            launcherPermissionRequest.launch(REQUIRED_PERMISSIONS)
        }
        setStringInput()
        setDateInput()
        setDropDown()
        setUploadButton()
        setSubmitButton()

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

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            activity?.baseContext as Context,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun setDateInput() {
        binding?.apply {
            setDate(etFormDateEnd, requireContext())
            setDate(etFormDateStart, requireContext())
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setStringInput() {
        binding?.apply {
            etFormLocation.showSoftInputOnFocus = false
            etFormLocation.setOnTouchListener { v, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val navigateAction = FormFragmentDirections
                            .actionFormFragmentToFormLocationFragment(etFormLocation.text.toString())
                        findNavController().navigate(navigateAction)
                    }
                }
                v?.onTouchEvent(event) ?: true
            }

            if (etFormLocation.text?.isNotEmpty() == true) {
                etFormLocation.setOnTouchListener { v, event ->
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {
                        }
                    }
                    v?.onTouchEvent(event) ?: true
                }
            }
        }
    }

    private fun setDropDown() {
        binding?.apply {
            // access the spinner
            val categories = resources.getStringArray(R.array.Categories)
            spCategories.item = categories.toMutableList() as List<Any>?
            spCategories.apply {
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?, position: Int, id: Long
                    ) {
                        category = categories[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        errorText = "On Nothing Selected"
                    }
                }
            }
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

    @SuppressLint("ClickableViewAccessibility")
    private fun setUploadButton() {
        binding?.apply {
            btnUploadImage.setOnClickListener {
                startGallery()
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    @SuppressLint("SetTextI18n")
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            if (result.data?.clipData != null) {
                val count = result.data!!.clipData!!.itemCount

                for (i in 0 until count) {
                    val imageUri: Uri = result.data!!.clipData!!.getItemAt(i).uri

                    imagesUri.add(imageUri)
                    binding?.apply {
                        btnUploadImage.visibility = visibility(false)
                        tvUploadImages.apply {
                            text = if (count > 3) {
                                "3 file/s attached"
                            } else {
                                "$count file/s attached"
                            }

                        }
                    }
                }
            }
        }
    }

    private fun setSubmitButton() {
        viewModel.time.observe(viewLifecycleOwner) {
            timeNow = it
        }

        binding?.apply {
            btnSubmit.setOnClickListener {
                lifecycleScope.launch {
                    uploadToFirebaseStorage(imagesUri as List<Uri>?)
                }
            }

        }
    }

    private fun uploadForm() {

        googleAuth = Firebase.auth
        val firebaseUser = googleAuth.currentUser

        binding?.apply {
            val mission = Mission(
                firebaseUser?.uid,
                etFormActivity.text.toString(),
                etFormLocation.text.toString(),
                citySelected,
                provinceSelected,
                etFormDateStart.text.toString(),
                etFormDateEnd.text.toString(),
                imagesLink as List<String?>,
                category,
                etRequirement.text.toString(),
                etNotes.text.toString(),
                etFormAmount.text.toString()
            )
            viewModel.postMision(mission)
            viewModel.isSuccess.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { success ->
                    if (success) {
                        showSuccessDialog(requireContext())
                        imagesUri.clear()
                    } else {
                        Toast.makeText(activity, "Error when upload form", Toast.LENGTH_SHORT)
                            .show()
                        imagesUri.clear()
                    }
                }
            }

            viewModel.error.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { message ->
                    showMessage(message)
                }
            }
        }

    }

    private fun showMessage(message: String) {
        Toast.makeText(activity, "viewmodel : " + message, Toast.LENGTH_SHORT).show()
    }

//    private suspend fun uploadImages(){
//        for (i in 0 until imagesUri.size) {
//            if (i < 3) {
//                scope.launch {
//                    async {
//                        uploadToFirebaseStorage(imagesUri[i], timeNow + "(${i + 1})")
//                    }
//                }
//            }
//        }
//    }

    private fun uploadToFirebaseStorage(listUri: List<Uri>?) {
        var link: String
        val fileName = UUID.randomUUID().toString()
        var countImages = 0
        for(i in 0 until listUri?.size!!){
            val ref = FirebaseStorage.getInstance().getReference("images/${fileName}(${i+1})")
            val uploadTask = ref.putFile(listUri[i]!!)
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
                    imagesLink.add(link)

                    Log.v("Link", "===>$link")
                    Toast.makeText(activity, link, Toast.LENGTH_SHORT).show()
                    countImages++
                    if(countImages == listUri.size){
                        uploadForm()
                    }
                    uploadError = false
                } else {
                    Toast.makeText(activity, "Error when upload files", Toast.LENGTH_SHORT).show()
                    uploadError = true
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
        const val EXTRA_LOC = "extra_location"
        const val EXTRA_TOKEN = "extra_token"
    }
}