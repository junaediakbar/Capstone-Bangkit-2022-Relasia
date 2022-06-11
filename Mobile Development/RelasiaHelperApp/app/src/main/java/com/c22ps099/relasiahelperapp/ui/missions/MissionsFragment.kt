package com.c22ps099.relasiahelperapp.ui.missions

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.adapter.FilterPagerAdapter
import com.c22ps099.relasiahelperapp.data.MissionRepository
import com.c22ps099.relasiahelperapp.databinding.FragmentMissionsBinding
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MissionsFragment : Fragment() {

    private var binding: FragmentMissionsBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String

    private val missionsViewModel by viewModels<MissionsViewModel> {
        MissionsViewModel.Factory(
            MissionRepository(ApiConfig.getApiService()),
            uid
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMissionsBinding.inflate(
            inflater, container, false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) uid = auth.currentUser?.uid.toString()

        if (firebaseUser == null) {
            val navigateAction = MissionsFragmentDirections
                .actionMissionsFragmentToLoginFragment()
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

        val viewPager = binding?.viewPager
        viewPager?.adapter = FilterPagerAdapter(activity as AppCompatActivity)

        val tabs = binding?.tabs
        tabs?.setSelectedTabIndicatorColor(Color.parseColor("#3587A4"))
        if (tabs != null && viewPager != null) {
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        missionsViewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }

        binding?.apply {

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3,
        )
    }
}