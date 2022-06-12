package com.c22ps099.relasiahelpseekerapp.data.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.c22ps099.relasiahelpseekerapp.ui.missions.MissionStatusFragment

class FilterPagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = MissionStatusFragment()
        fragment.arguments = Bundle().apply {
            putInt(MissionStatusFragment.ARG_SECTION_NUMBER, position)
        }
        return fragment
    }
}