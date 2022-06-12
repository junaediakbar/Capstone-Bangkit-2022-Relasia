package com.c22ps099.relasiahelperapp.ui.missionDetail

import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.adapter.SlideImageAdapter
import com.c22ps099.relasiahelperapp.data.Mission
import com.c22ps099.relasiahelperapp.databinding.FragmentMissionDetailBinding
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.network.responses.MissionDetailResponse
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.c22ps099.relasiahelperapp.ui.main.BaseActivity
import com.c22ps099.relasiahelperapp.utils.DateFormatter
import com.c22ps099.relasiahelperapp.utils.timeStampDialog
import com.c22ps099.relasiahelperapp.utils.toTitleCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MissionDetailFragment : Fragment() {

    companion object {
        const val EXTRA_MISSION = "extra_mission"
    }

    private var binding: FragmentMissionDetailBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    private lateinit var helpseekerWa: String
    private lateinit var idMission: String

    private val missionDetailViewModel by viewModels<MissionDetailViewModel> {
        MissionDetailViewModel.Factory(
            arguments?.getParcelable<MissionDataItem>(EXTRA_MISSION) as MissionDataItem,
            activity?.applicationContext as Application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMissionDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) uid = auth.currentUser?.uid.toString()

        val mission = arguments?.getParcelable<MissionDataItem>(EXTRA_MISSION) as MissionDataItem
        val missionString = Mission(mission.id)
        idMission = mission.id

        if (firebaseUser == null) {
            val navigateAction = MissionDetailFragmentDirections
                .actionMissionDetailFragmentToLoginFragment()
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

        missionDetailViewModel.apply {
            showDetailMission(mission.id)
            detailMission.observe(viewLifecycleOwner) { mission ->
                setMissionDetailData(mission)
            }
            isSuccess.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { success ->
                    if (success) {
                        showSuccessDialog()
                    }
                }
            }
            isBookMission.observe(viewLifecycleOwner) { book ->
                binding?.apply {
                    btnBookmark.visibility = swapBookButton(!book)
                    btnUnbookmark.visibility = swapBookButton(book)
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                binding?.btnApply?.isEnabled = !it
                binding?.btnBookmark?.isEnabled = !it
                binding?.btnUnbookmark?.isEnabled = !it
                showLoading(it)
            }
            error.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { error ->
                    if (error.isNotEmpty()) {
                        Toast.makeText(
                            context,
                            error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding?.apply {
            fabBack.setOnClickListener {
                activity?.onBackPressed()
            }
            btnApply.setOnClickListener {
                if (btnApply.text == "Apply") {
                    applyVolunteerToMission(missionString)
                } else if (btnApply.text == "Send WhatsApp To Helpseeker") run {
                    waHelpseeker()
                }
            }
            btnBookmark.setOnClickListener {
                missionDetailViewModel.setBookMission(mission, true)
                Toast.makeText(
                    context,
                    getString(R.string.add_bookmark),
                    Toast.LENGTH_SHORT
                ).show()
            }
            btnUnbookmark.setOnClickListener {
                missionDetailViewModel.setBookMission(mission, false)
                Toast.makeText(
                    context,
                    getString(R.string.delete_bookmark),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setMissionDetailData(mission: MissionDetailResponse) {
        binding?.apply {
            if (mission.featuredImage?.size != 0) {
                mission.featuredImage?.get(0)
            } else {
                listOf("")
                mission.featuredImage = listOf("")
            }
            rvImages.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = SlideImageAdapter(mission.featuredImage as List<String>)
            }

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(rvImages)

            tvMissionTitle.text = mission.title
            tvMissionDate.text =
                DateFormatter.formatDate(mission.startDate) + " - " + DateFormatter.formatDate(
                    mission.endDate
                )
            tvMissionCity.text = mission.city + ", " + mission.province
            var countAppliedVolunteer = 0
            var statusCurrentVolunteer = ""
            for (volunteer in mission.volunteers) {
                if (volunteer.status == "accepted") {
                    countAppliedVolunteer += 1
                }
                if (volunteer.id == uid) {
                    statusCurrentVolunteer = volunteer.status
                }
            }
            btnApply.isEnabled = when (statusCurrentVolunteer) {
                "pending" -> false
                "rejected" -> false
                else -> true
            }
            btnApply.text = when (statusCurrentVolunteer) {
                "pending" -> toTitleCase(statusCurrentVolunteer)
                "rejected" -> toTitleCase(statusCurrentVolunteer)
                else -> "Apply"
            }
            if (statusCurrentVolunteer == "accepted") run {
                btnApply.text = "Send WhatsApp To Helpseeker"
                helpseekerWa = mission.helpseeker.phone
                btnApply.setBackgroundColor(Color.rgb(0, 191, 166))
            }
            tvApplicant.text = countAppliedVolunteer.toString() + "/" + mission.numberOfNeeds
            tvMissionReq.text = mission.requirement
            tvMissionNote.text = mission.note
            tvMissionCp.text = mission.helpseeker.phone + " (" + mission.helpseeker.name + ")"
            tvMissionCategory.text = mission.category
            pbApplicant.max = mission.numberOfNeeds.toInt()
            pbApplicant.progress = countAppliedVolunteer
            if (countAppliedVolunteer == mission.numberOfNeeds.toInt()) {
                pbApplicant.progressTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.red_200))
                btnApply.isEnabled = false
            }
        }
    }

    private fun swapBookButton(isBook: Boolean): Int {
        return if (isBook) View.VISIBLE else View.INVISIBLE
    }

    private fun applyVolunteerToMission(mission: Mission) {
        missionDetailViewModel.applyMission(uid, mission)
    }

    private fun waHelpseeker() {
        try {
            context?.packageManager?.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
            helpseekerWa.drop(1)
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://wa.me/62${helpseekerWa}?text=Hello%20I'm%20volunteer%20from%20Relasia")
            )
            startActivity(intent)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
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
        tvTime.text = timeStampDialog
        tvId.text = "ID Mission :\n${idMission}"
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
}