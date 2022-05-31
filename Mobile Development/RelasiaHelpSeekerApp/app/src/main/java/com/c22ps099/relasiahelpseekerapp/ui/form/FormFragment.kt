package com.c22ps099.relasiahelpseekerapp.ui.form

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentFormBinding
import com.c22ps099.relasiahelpseekerapp.ui.home.PostsViewModel
import com.c22ps099.relasiahelpseekerapp.utils.itemsKab
import com.c22ps099.relasiahelpseekerapp.utils.itemsProv
import java.util.*

class FormFragment : Fragment() {

    private val viewModel by viewModels<FormViewModel> {
        FormViewModel.Factory(getString(R.string.auth, token))
    }

    private var token: String? = ""

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
        setStringInput()
        setImageInput()
        setDateInput()
        setChipButton()
        setDropDown()
    }

    @SuppressLint("ClickableViewAccessibility")
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
                                etFormDateStart.setText("$mdayOfMonth/$mmonth/$myear")
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
    private fun setImageInput() {
        binding?.apply {
            etFormPhoto.setOnTouchListener { v, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {

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

            btnSubmit.setOnClickListener {
                val dialog = Dialog(requireContext())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.dialog_form_success)
                dialog.show()
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
                    context,
                    android.R.layout.simple_spinner_item, categories
                )
                adapter = adp
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {

                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }
            }
            spFormProvince.apply {
                val provinces = itemsProv
                val adp = ArrayAdapter(
                    context,
                    android.R.layout.simple_spinner_item, provinces
                )
                adapter = adp
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
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
                        context,
                        android.R.layout.simple_spinner_item, cities
                    )
                    adapter = adp
                    onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View, position: Int, id: Long
                        ) {

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

    companion object {
        const val EXTRA_LOC = "extra_location"
        const val EXTRA_TOKEN = "extra_token"
    }

}