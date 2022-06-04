package com.c22ps099.relasiahelperapp.ui.register

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.databinding.FragmentRegisterBinding
import com.c22ps099.relasiahelperapp.ui.home.HomeFragment
import com.c22ps099.relasiahelperapp.ui.login.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var token: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)

        auth = FirebaseAuth.getInstance()
    }

    private var binding: FragmentRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            tvLogin.setOnClickListener {
                val navigateAction = RegisterFragmentDirections
                    .actionRegisterFragmentToLoginFragment()
                findNavController().navigate(navigateAction)

                val mLoginFragment = LoginFragment()
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.nav_host_fragment,
                        mLoginFragment,
                        RegisterFragment::class.java.simpleName
                    )
                    setReorderingAllowed(true)
                    commit()
                }
            }
            btnRegister.setOnClickListener {
                signUp()
            }
        }
    }

    private fun signUp() {
        binding?.apply {
            val email = etEmail.text.toString()
            val pass = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(activity, "Email and Password can't be blank", Toast.LENGTH_SHORT)
                    .show()
                return
            }

            if (pass != confirmPassword) {
                Toast.makeText(
                    activity,
                    "Password and Confirm Password do not match",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return
            }

            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d(LoginFragment.TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                        token = FirebaseAuth.getInstance().currentUser!!.getIdToken(true).toString()
                        Log.e("token", "$token")
                    } else {
                        Log.w(LoginFragment.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            activity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val navigateAction = RegisterFragmentDirections
                .actionRegisterFragmentToHomeFragment()
            navigateAction.token = currentUser.getIdToken(true).toString()
            navigateAction.userId = currentUser.uid
            findNavController().navigate(navigateAction)

            val mHomeFragment = HomeFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, mHomeFragment, HomeFragment::class.java.simpleName)
                setReorderingAllowed(true)
                commit()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
}