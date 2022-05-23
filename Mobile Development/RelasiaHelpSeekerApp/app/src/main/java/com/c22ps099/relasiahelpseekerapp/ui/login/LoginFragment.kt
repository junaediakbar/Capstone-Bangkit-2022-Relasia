package com.c22ps099.relasiahelpseekerapp.ui.login

import android.os.Bundle
import androidx.fragment.app.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c22ps099.relasiahelpseekerapp.R
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            tvRegisterNow.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment,
                    null,
                    null,
                    FragmentNavigatorExtras(
                        usernameInput to usernameInput.transitionName,
                        passwordInput to passwordInput.transitionName,
                        btnLogin to btnLogin.transitionName,
                        tvRegisterNow to tvRegisterNow.transitionName,
                    )
                )
            }
            btnLogin.setOnClickListener {
                val navigateAction = LoginFragmentDirections
                    .actionLoginFragmentToHomeFragment()
                navigateAction.token = "token"
                findNavController().navigate(navigateAction)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

}