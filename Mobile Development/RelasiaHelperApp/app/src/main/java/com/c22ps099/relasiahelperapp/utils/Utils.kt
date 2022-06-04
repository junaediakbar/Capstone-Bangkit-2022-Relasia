package com.c22ps099.relasiahelperapp.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.navigation.findNavController
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.ui.home.HomeFragment
import com.c22ps099.relasiahelperapp.ui.missionDetail.MissionDetailFragmentDirections

fun showSuccessDialog(context: Context, view: View) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.dialog_success)
    dialog.show()
    val btnHome = dialog.findViewById<Button>(R.id.btn_home)
    val btnStatus = dialog.findViewById<Button>(R.id.btn_status)
    btnHome.setOnClickListener {
        val navigateAction = MissionDetailFragmentDirections
            .actionMissionDetailFragmentToHomeFragment()
        view.findNavController().navigate(navigateAction)
        dialog.hide()
    }
    btnStatus.setOnClickListener {
        val navigateAction = MissionDetailFragmentDirections
            .actionMissionDetailFragmentToMissionsFragment()
        view.findNavController().navigate(navigateAction)
        dialog.hide()
    }
}