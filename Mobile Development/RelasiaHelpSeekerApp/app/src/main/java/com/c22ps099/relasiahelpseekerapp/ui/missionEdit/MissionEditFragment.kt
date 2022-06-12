package com.c22ps099.relasiahelpseekerapp.ui.missionEdit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentMissionEditBinding
import com.c22ps099.relasiahelpseekerapp.model.EditableMission
import com.c22ps099.relasiahelpseekerapp.ui.main.MainActivity


class MissionEditFragment : Fragment() {
    private var binding: FragmentMissionEditBinding? = null
    private var token: String? = ""

    private val viewModel by viewModels<MissionEditViewModel> {
        MissionEditViewModel.Factory(getString(R.string.auth, token))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMissionEditBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mission = arguments?.getParcelable<MissionItem>(EXTRA_MISSION) as MissionItem
        setMission(mission)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding?.apply {
            btnSubmit.setOnClickListener {
                trySubmit(mission)
            }
        }

        viewModel.apply {
            isSuccess.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { success ->
                    if (success) {
                        Toast.makeText(activity, "Mission Updated Successfully", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.overridePendingTransition(0, 0)
                    }else{
                        Toast.makeText(activity, "Error when Updated Mission", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun trySubmit(mission: MissionItem) {
        binding?.apply {
            val editableMission = EditableMission(
                id=mission.id,
                numberOfNeeds = etFormAmount.text.toString(),
                note = etNotes.text.toString(),
                requirement = etRequirement.text.toString()
            )
            viewModel.editMisison(editableMission)

        }
    }

    private fun setMission(mission: MissionItem){
        binding?.apply {
            etFormAmount.setText(mission.numberOfNeeds)
            etNotes.setText(mission.note)
            etRequirement.setText(mission.requirement)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val EXTRA_MISSION ="extra_mission"
    }
}