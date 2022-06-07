package com.c22ps099.relasiahelperapp.ui.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.data.MissionRepository
import com.c22ps099.relasiahelperapp.databinding.FragmentMissionsBinding
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.ui.MissionFactory
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MissionsFragment : Fragment() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var missionsViewModel: MissionsViewModel
    private var binding: FragmentMissionsBinding? = null
    private lateinit var googleAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        missionsViewModel =
            ViewModelProvider(
                this, MissionFactory(
                    MissionRepository(
                        ApiConfig.getApiService()
                    )
                )
            )[MissionsViewModel::class.java]
        binding = FragmentMissionsBinding.inflate(
            inflater, container, false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleAuth = Firebase.auth
        val firebaseUser = googleAuth.currentUser

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

        binding?.apply {

        }
    }
}