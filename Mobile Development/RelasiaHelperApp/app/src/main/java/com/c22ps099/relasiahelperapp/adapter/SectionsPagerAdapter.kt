package com.c22ps099.relasiahelperapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.c22ps099.relasiahelperapp.ui.missionStatus.MissionStatusFragment

class SectionsPagerAdapter(activity: AppCompatActivity, val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = MissionStatusFragment()
        fragment.arguments = Bundle().apply {
            putString(MissionStatusFragment.ARG_USERNAME, username)
            putInt(MissionStatusFragment.ARG_SECTION_NUMBER, position)
        }
        return fragment
    }
}