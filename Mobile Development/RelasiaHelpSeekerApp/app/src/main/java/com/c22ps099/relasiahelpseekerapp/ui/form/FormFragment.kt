package com.c22ps099.relasiahelpseekerapp.ui.form

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
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
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentFormBinding
import com.c22ps099.relasiahelpseekerapp.misc.*
import com.c22ps099.relasiahelpseekerapp.model.Mission
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.util.*


class FormFragment : Fragment() {

    private val viewModel by viewModels<FormViewModel> {
        FormViewModel.Factory(getString(R.string.auth, token))
    }

    private var token: String? = ""

    private var timeNow: String? = ""

    private var category: String? = ""

    private var imagesUri = mutableListOf<Uri>()
    private var imagesLink = mutableListOf<String?>()

    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private var binding: FragmentFormBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(inflater, container, false)
        FirebaseApp.initializeApp(requireContext())

        return binding?.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!allPermissionGranted()) {
            launcherPermissionRequest.launch(REQUIRED_PERMISSIONS)
        }

        setStringInput()
        setDateInput()
        setChipButton()
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

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    private fun setDateInput() {
        binding?.apply {
            etFormDateEnd.showSoftInputOnFocus = false
            etFormDateEnd.setOnTouchListener { v, event ->
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val datePickerDialog = DatePickerDialog(
                            requireContext(),
                            { _, myear, mmonth, mdayOfMonth ->
                                etFormDateEnd.setText("$mdayOfMonth/$mmonth/$myear")
                            }, year, month, day
                        )
                        datePickerDialog.show()
                    }
                }

                v?.onTouchEvent(event) ?: true
            }

            etFormDateStart.showSoftInputOnFocus = false
            etFormDateStart.setOnTouchListener { v, event ->
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val datePickerDialog = DatePickerDialog(
                            requireContext(),
                            { _, myear, mmonth, mdayOfMonth ->
                                val dd = if (mdayOfMonth > 10) {
                                    mdayOfMonth
                                } else "0$mdayOfMonth"
                                val mm = if (mmonth > 10) {
                                    mdayOfMonth
                                } else "0$mmonth"

                                etFormDateStart.setText("$dd/$mm/$myear")
                            }, year, month, day
                        )
                        datePickerDialog.show()
                    }
                }

                v?.onTouchEvent(event) ?: true
            }
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
        val location = FormFragmentArgs.fromBundle(arguments as Bundle).location
        binding?.apply {
            etFormLocation.setText(location)
        }
    }

    private fun setDropDown() {
        binding?.apply {
            // access the spinner
            spCategories.apply {
                val categories = resources.getStringArray(R.array.Categories)
                val adp = ArrayAdapter(
                    activity?.applicationContext!!,
                    android.R.layout.simple_spinner_item, categories
                )
                adapter = adp
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?, position: Int, id: Long
                    ) {
                        category = categories[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }
            }
            spFormProvince.apply {
                val provinces = itemsProv
                val adp = ArrayAdapter(
                    activity?.applicationContext!!,
                    android.R.layout.simple_spinner_item, provinces
                )
                adapter = adp
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?, position: Int, id: Long
                    ) {
                        viewModel.updateProvince(provinces[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }
            }

            viewModel.province.observe(viewLifecycleOwner) {
                spFormCity.apply {
                    val cities = itemsKab(it)
                    val adp = ArrayAdapter(
                        activity?.applicationContext!!,
                        android.R.layout.simple_spinner_item, cities
                    )
                    adapter = adp
                    onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View?, position: Int, id: Long
                        ) {
                            viewModel.updateCity(cities[position])
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            // write code to perform some action
                        }
                    }
                }
            }
        }
    }

    private fun setChipButton() {
        binding?.apply {
            btnPersyaratan.setOnClickListener {
                val bottomSheet = RequirementsDialog()
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
            btnNotes.setOnClickListener {
                val bottomSheet = NotesDialog()
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
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
//              scope.launch { uploadImages() }
               uploadForm()
            }
        }
    }

    private fun uploadForm() {

        var volunteers = mapOf("one" to "test", "two" to "test2", "three" to "ssas")
        binding?.apply {
//            val mission = MissionItem(
//                etFormDateEnd.text.toString(),
//                "Notes:bla bla",
//                etFormLocation.text.toString(),
//                viewModel.city.toString(),
//                "requirements: bla",
//                etFormActivity.text.toString(),
//                volunteers,
//                imagesLink,
//                etFormAmount.text.toString(),
//                viewModel.province.toString(),
//                "helpseeker.id",
//                category,
//                etFormDateStart.text.toString(),
//                timeStamp
//            )
            val featuredImgs : List<String?>?= listOf("test","test2")

            val gson = Gson()
            val mission = Mission(
                "helpseeker.id",
                "Tsunami Palu 2019",
                "Jl. Soekarno Hatta",
                "Palu",
                "requirements: bla",
               "12-21-1212",
                "12-21-1212",
                featuredImgs,
                "helpseeker-id",
                    "apa" ,
                "apa",
                "10"
            )
                viewModel.postMision(mission)
                viewModel.isSuccess.observe(viewLifecycleOwner) {
                    it.getContentIfNotHandled()?.let { success ->
                        if (success) {
                            showSuccessDialog(requireContext())
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
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun uploadImages() {
        for (i in 0 until imagesUri.size) {
            if (i < 3) {
                scope.launch {
                    uploadToFirebaseStorage(imagesUri[i], timeNow + "(${i + 1})")
                }
            }
        }
    }

    private fun uploadToFirebaseStorage(imgUri: Uri?, filename: String?) {
        var link: String
        val ref = FirebaseStorage.getInstance().getReference("images/${filename}")
        val uploadTask = ref.putFile(imgUri!!)

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

            } else {
                Toast.makeText(activity, "Error when upload files", Toast.LENGTH_SHORT).show()
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