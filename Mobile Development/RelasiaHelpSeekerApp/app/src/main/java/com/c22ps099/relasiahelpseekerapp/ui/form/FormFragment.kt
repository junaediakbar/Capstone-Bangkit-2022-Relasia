package com.c22ps099.relasiahelpseekerapp.ui.form

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentFormBinding
import java.util.*

class FormFragment : Fragment() {

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
        binding?.apply {
            btnPersyaratan.setOnClickListener {
                val bottomSheet = RequirementsDialog()
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
            btnNotes.setOnClickListener {
                val bottomSheet = NotesDialog()
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }

            etFormLocation.showSoftInputOnFocus=false
            etFormLocation.setOnTouchListener { v, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->{
                        val navigateAction = FormFragmentDirections
                            .actionFormFragmentToFormLocationFragment()
                        findNavController().navigate(navigateAction)
                    }
                }
                v?.onTouchEvent(event) ?: true
            }

           if(etFormLocation.text?.isNotEmpty() == true){
               etFormLocation.setOnTouchListener { v, event ->
                   when (event?.action) {
                       MotionEvent.ACTION_DOWN ->{

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

            etFormDateEnd.showSoftInputOnFocus=false
            etFormDateEnd.setOnTouchListener { v, event ->
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->{
                        val datePickerDialog = DatePickerDialog(requireContext(),
                            { view, myear, mmonth, mdayOfMonth ->
                                etFormDateEnd.setText("$mdayOfMonth/$mmonth/$myear")
                            }, year, month, day
                        )
                        datePickerDialog.show()
                    }
                }

                v?.onTouchEvent(event) ?: true
            }
            etFormDateStart.showSoftInputOnFocus=false
            etFormDateStart.setOnTouchListener { v, event ->
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->{
                        val datePickerDialog = DatePickerDialog(requireContext(),
                            { view, myear, mmonth, mdayOfMonth ->
                                etFormDateStart.setText("$mdayOfMonth/$mmonth/$myear")
                            }, year, month, day
                        )
                        datePickerDialog.show()
                    }
                }

                v?.onTouchEvent(event) ?: true
            }
        }
        val location =FormFragmentArgs.fromBundle(arguments as Bundle).location
        binding?.apply {
            etFormLocation.setText(location)
        }
    }

    companion object{
        const val EXTRA_LOC = "extra_location"
        const val EXTRA_TOKEN = "extra_token"
    }

}