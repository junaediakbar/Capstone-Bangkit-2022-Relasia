package com.c22ps099.relasiahelperapp.ui.missions

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.adapter.SectionsPagerAdapter
import com.c22ps099.relasiahelperapp.databinding.FragmentMissionsBinding
import com.google.android.material.tabs.TabLayoutMediator


class MissionsFragment : Fragment() {

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var missionsViewModel: MissionsViewModel
    private var binding: FragmentMissionsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        missionsViewModel =
            ViewModelProvider(this)[MissionsViewModel::class.java]
        binding = FragmentMissionsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

        }

        val viewPager = binding?.viewPager
        viewPager?.adapter = SectionsPagerAdapter(activity as AppCompatActivity, "Test User")
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> viewPager?.setBackgroundColor(Color.DKGRAY)
            Configuration.UI_MODE_NIGHT_NO -> viewPager?.setBackgroundColor(Color.LTGRAY)
            else -> viewPager?.setBackgroundColor(Color.TRANSPARENT)
        }

        val tabs = binding?.tabs
        tabs?.setSelectedTabIndicatorColor(Color.parseColor("#3587A4"))
        if (tabs != null && viewPager != null) {
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }
}