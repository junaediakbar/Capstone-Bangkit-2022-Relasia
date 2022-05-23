package com.c22ps099.relasiahelpseekerapp.ui.form

import android.view.WindowManager



import android.widget.EditText

import android.os.Bundle

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.c22ps099.relasiahelpseekerapp.R


class RequirementDialog : DialogFragment() {

    private var mEditText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_requirement_dialog, container)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Baca view yang dibuat di XML
        mEditText = view.findViewById(R.id.et_requirement)
        // Ambil argument dari bundle (yang ada di newInstance) lalu mengatur title dari Dialog
        // yang ditampilkan dengan data di dalam bundle
        val title: String? = getArguments()?.getString("title", "Enter Name")


        mEditText!!.requestFocus()
        getDialog()?.getWindow()?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
    }

    companion object {
        fun newInstance(title: String?): RequirementDialog {
            val frag = RequirementDialog()
            val args = Bundle()
            args.putString("title", title)
            frag.setArguments(args)
            return frag
        }
    }
}