package com.c22ps099.relasiahelpseekerapp.ui.register

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentRegisterBinding
import com.c22ps099.relasiahelpseekerapp.ui.home.HomeFragment
import com.c22ps099.relasiahelpseekerapp.ui.login.LoginFragment
import com.c22ps099.relasiahelpseekerapp.ui.login.LoginFragmentDirections
import com.c22ps099.relasiahelpseekerapp.view.editText.EditTextWithValidation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

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
        showLoading(false)
        binding?.apply {
            etEmail.setValidationCallback(object : EditTextWithValidation.InputValidation {
                override val errorMessage: String
                    get() = getString(R.string.message_validation_email)

                override fun validate(input: String): Boolean =
                    input.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(input).matches()
            })
            etPassword.setValidationCallback(object : EditTextWithValidation.InputValidation {
                override val errorMessage: String
                    get() = getString(R.string.message_validation_password)

                override fun validate(input: String): Boolean =
                    input.isNotEmpty() && input.length >= 6
            })
            tvLoginNow.setOnClickListener {
                val navigateAction = RegisterFragmentDirections
                    .actionRegisterFragmentToLoginFragment()
                findNavController().navigate(navigateAction)

                val mLoginFragment = LoginFragment()
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.container,
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
        showLoading(true)
        binding?.apply {
            val email =etEmail.text.toString()
            val pass = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(activity, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
                showLoading(false)
                return
            }

            if (pass != confirmPassword) {
                Toast.makeText(
                    activity,
                    "Password and Confirm Password do not match",
                    Toast.LENGTH_SHORT
                ).show()
                showLoading(false)
                return
            }

            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d(LoginFragment.TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                        showLoading(false)
                    } else {
                        Log.w(LoginFragment.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            activity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                        showLoading(false)
                    }
                }
        }

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val navigateAction = RegisterFragmentDirections
                .actionRegisterFragmentToHomeFragment()
            findNavController().navigate(navigateAction)

            val mHomeFragment = HomeFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.container, mHomeFragment, HomeFragment::class.java.simpleName)
                setReorderingAllowed(true)
                commit()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
}