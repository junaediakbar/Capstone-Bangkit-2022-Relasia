package com.c22ps099.relasiahelperapp.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.data.Volunteer
import com.c22ps099.relasiahelperapp.databinding.FragmentProfileBinding
import com.c22ps099.relasiahelperapp.network.responses.VolunteerDetailData
import com.c22ps099.relasiahelperapp.ui.custom.MyEditTextValidation
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.c22ps099.relasiahelperapp.ui.main.BaseActivity
import com.c22ps099.relasiahelperapp.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.*


class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth

    private var token: String? = ""
    private var getFile: File? = null
    private var avatarLink: String = ""
    private var avatarLinkOld: String = ""
    private var isRegisteredUser: Boolean = false

    private var dataName: String? = null
    private var dataGender: String? = null
    private var dataBirthyear: String? = null
    private var dataProvince: String? = null
    private var dataCity: String? = null
    private var dataAddress: String? = null
    private var dataNohp: String? = null

    private val profileViewModel by viewModels<ProfileViewModel> {
        ProfileViewModel.Factory(
            getString(R.string.auth, token),
            FirebaseAuth.getInstance().currentUser?.uid.toString()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding?.root
    }

    private val checkInputCallBack = object : MyEditTextValidation.InputValidation {
        override val errorMessage: String
            get() = getString(R.string.input_cant_blank)

        override fun validate(input: String): Boolean = input.isNotEmpty()
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionGranted()) {
            launcherPermissionRequest.launch(REQUIRED_PERMISSIONS)
        }

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        auth = FirebaseAuth.getInstance()

        if (firebaseUser == null) {
            val navigateAction = ProfileFragmentDirections
                .actionProfileFragmentToLoginFragment()
            findNavController().navigate(navigateAction)

            val mLoginFragment = LoginFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.nav_host_fragment,
                    mLoginFragment,
                    LoginFragment::class.java.simpleName
                )
                setReorderingAllowed(true)
                commit()
            }
        }

        profileViewModel.apply {
            getVolunteer(auth.currentUser?.uid.toString())
            volunteer.observe(viewLifecycleOwner) {
                setDataVolunteer(it)
                avatarLink = it?.picture.toString()
                isRegisteredUser = true
                avatarLinkOld = it?.picture.toString()
            }
            isSuccess.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { success ->
                    if (success) {
                        showSuccessDialog()
                    }
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }

        val genders = resources.getStringArray(R.array.Genders)

        binding?.apply {
            etProfileName.setValidationCallback(checkInputCallBack)
            etProfileName.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            etProfilePhone.setValidationCallback(checkInputCallBack)
            etProfilePhone.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            etProfileAddress.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            tvProfileEmail.text = auth.currentUser?.email
            spProfileGender.item = genders.toMutableList().toList()
            spProfileGender.apply {
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?, position: Int, id: Long
                    ) {
                        dataGender = genders[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        errorText = "On Nothing Selected"
                    }
                }
            }
            etProfileBirthdate.showSoftInputOnFocus = false
            etProfileBirthdate.setOnTouchListener { v, event ->
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val datePickerDialog = DatePickerDialog(
                            requireContext(),
                            { _, myear, mmonth, mdayOfMonth ->
                                etProfileBirthdate.setText("$mdayOfMonth/$mmonth/$myear")
                                dataBirthyear = myear.toString()
                            }, year, month, day
                        )
                        datePickerDialog.show()
                    }
                }
                v?.onTouchEvent(event) ?: true
            }
            spFormProvince.apply {
                val provinces = itemsProv
                item = provinces.toMutableList().toList()
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?, position: Int, id: Long
                    ) {
                        profileViewModel.updateProvince(provinces[position])
                        dataProvince = provinces[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        errorText = "On Nothing Selected"
                    }
                }
            }
            profileViewModel.province.observe(viewLifecycleOwner) {
                spFormCity.apply {
                    val cities = itemsKab(it)
                    item = cities.toMutableList().toList()
                    onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View?, position: Int, id: Long
                        ) {
                            profileViewModel.updateCity(cities[position])
                            dataCity = cities[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            errorText = "On Nothing Selected"
                        }
                    }
                }
            }
            btnFoundation.setOnClickListener {
                val navigateAction = ProfileFragmentDirections
                    .actionProfileFragmentToFoundationDataFragment()
                findNavController().navigate(navigateAction)
            }
            btnSave.setOnClickListener {
                if (getFile != null) {
                    uploadToFirebaseStorage(getFile)
                } else if (getFile == null && avatarLink != "") {
                    trySubmit()
                } else {
                    Toast.makeText(activity, "Please input an image!", Toast.LENGTH_SHORT).show()
                }
            }
            ibAddPhoto.setOnClickListener {
                startGallery()
            }
            btnLogout.setOnClickListener {
                auth.signOut()

                activity?.finish()
                activity?.overridePendingTransition(0, 0)
                val intent = Intent(activity, BaseActivity::class.java)
                startActivity(intent)
                activity?.overridePendingTransition(0, 0)
            }
        }
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
            binding?.imgProfile?.let {
                Glide.with(binding?.imgProfile?.context!!)
                    .load(selectedImg)
                    .placeholder(R.drawable.no_image_placeholder)
                    .circleCrop()
                    .into(it)
            }
        }
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

    private fun trySubmit() {
        binding?.apply {
            val isNameValid = etProfileName.validateInput()
            val isPhoneValid = etProfilePhone.validateInput()
            dataName = etProfileName.text.toString()
            dataAddress = etProfileAddress.text.toString()
            dataNohp = etProfilePhone.text.toString()

            if (!isNameValid || !isPhoneValid || dataCity == "" || dataProvince == "" || dataGender == "" || dataAddress == "" || dataBirthyear == "") {
                showSnackbar(root, getString(R.string.input_cant_blank))
                return
            }
        }
        val volunteer = Volunteer(
            avatarLink,
            dataAddress,
            dataBirthyear,
            dataGender,
            dataCity,
            dataProvince,
            dataNohp,
            dataName,
            FirebaseAuth.getInstance().currentUser?.uid.toString()
        )

        if (isRegisteredUser) {
            if (avatarLinkOld != avatarLink) {
                val storageReference: StorageReference = FirebaseStorage.getInstance()
                    .getReferenceFromUrl(avatarLinkOld)
                storageReference.delete().addOnSuccessListener {
                    Log.d("storage", "Delete done")
                }.addOnFailureListener {
                    Log.d("storage", "error while deleting")
                }
            }
            profileViewModel.updateVolunteer(volunteer)
        } else {
            profileViewModel.addNewVolunteer(volunteer)
        }
    }

    private fun setDataVolunteer(volunteer: VolunteerDetailData?) {
        binding?.apply {
            etProfileName.setText(volunteer?.name)
            tvProfileEmail.text = auth.currentUser?.email
            etProfilePhone.setText(volunteer?.phone)
            etProfileAddress.setText(volunteer?.address)
            spFormProvince.setSelection(itemsProv.indexOf("${volunteer?.province}"))
            val listOfKabs = itemsKab("${volunteer?.province}")

            spFormCity.item = itemsKab("${volunteer?.province}").toMutableList() as List<Any>?
            spFormCity.setSelection(listOfKabs.indexOf("${volunteer?.city}"))

            val genders = resources.getStringArray(R.array.Genders)
            spProfileGender.item = genders.toMutableList().toList()
            spProfileGender.setSelection(genders.indexOf("${ volunteer?.gender }"))

            imgProfile.let {
                Glide.with(imgProfile.context)
                    .load(volunteer?.picture)
                    .placeholder(R.drawable.no_image_placeholder)
                    .circleCrop()
                    .into(it)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showSuccessDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_form_success)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val tvId = dialog.findViewById<TextView>(R.id.tv_success_id)
        val tvTime = dialog.findViewById<TextView>(R.id.tv_dialog_time)
        val tvDesc = dialog.findViewById<TextView>(R.id.tv_dialog_desc)
        val btnToHome = dialog.findViewById<Button>(R.id.btn_to_home)
        btnToHome.setOnClickListener {
            dialog.dismiss()
            activity?.finish()
            activity?.overridePendingTransition(0, 0)
            val intent = Intent(activity, BaseActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(0, 0)
            dialog.hide()
        }

        tvTime.text = timeStampDialog
        tvId.text = "ID Volunteer :\n${auth.currentUser?.uid}"
        tvDesc.text = "Welcome to Relasia, Let's help someone!"
        dialog.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
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
    }
}