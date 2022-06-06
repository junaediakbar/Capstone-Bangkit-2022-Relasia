package com.c22ps099.relasiahelperapp.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.data.Volunteer
import com.c22ps099.relasiahelperapp.databinding.FragmentProfileBinding
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.c22ps099.relasiahelperapp.utils.itemsKab
import com.c22ps099.relasiahelperapp.utils.itemsProv
import com.c22ps099.relasiahelperapp.utils.showSnackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*

class ProfileFragment : Fragment() {

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

    private var binding: FragmentProfileBinding? = null
    private lateinit var googleAuth: FirebaseAuth
    private lateinit var emailAuth: FirebaseAuth

    private var token: String? = ""

    private var category: String? = ""

    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    private var dataName : String? = null
    private var dataGender : String? = null
    private var dataBirthyear : String? = null
    private var dataProvince : String? = null
    private var dataCity : String? = null
    private var dataAddress : String? = null
    private var dataNohp : String? = null

    private val profileViewModel by viewModels<ProfileViewModel> {
        ProfileViewModel.Factory(getString(R.string.auth, token))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding?.root
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionGranted()) {
            launcherPermissionRequest.launch(REQUIRED_PERMISSIONS)
        }

        googleAuth = Firebase.auth
        val firebaseUser = googleAuth.currentUser

        emailAuth = FirebaseAuth.getInstance()

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
        val genders = resources.getStringArray(R.array.Genders)

        binding?.apply {
            tvProfileEmail.text = emailAuth.currentUser?.email
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
                        // write code to perform some action
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
                        // write code to perform some action
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
                            // write code to perform some action
                        }
                    }
                }
            }
            btnFoundation.setOnClickListener {

            }
            btnSave.setOnClickListener {
                addNewVolunteer()
            }
            btnLogout.setOnClickListener {
                googleAuth.signOut()

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

    private fun addNewVolunteer() {
        if (emailAuth.currentUser != null) {
            val id = emailAuth.currentUser?.uid
            Log.e("id", "===>>>$id")

            dataName = binding?.etProfileName?.text.toString()
            dataAddress = binding?.etProfileAddress?.text.toString()
            dataNohp = binding?.etProfilePhone?.text.toString()
            val volunteer = Volunteer(
                dataAddress,
                dataBirthyear,
                dataGender,
                dataCity,
                dataProvince,
                dataNohp,
                dataName,
                id
            )
            profileViewModel.addNewVolunteer(volunteer)
            profileViewModel.isSuccess.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { success ->
                    if (success) {

                    }
                }
            }

            profileViewModel.error.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { message ->
                    showMessage(message)
                }

            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}