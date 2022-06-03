package com.c22ps099.relasiahelpseekerapp.ui.register

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterFragment : Fragment() {

    private lateinit var  auth: FirebaseAuth

    private var token : String? =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)

        auth= FirebaseAuth.getInstance()
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
            tvLoginNow.setOnClickListener {
                findNavController().navigateUp()
            }
            btnRegister.setOnClickListener{
                register()
            }
        }
    }

    private fun register() {
        binding?.apply {
            val email = emailInput.text.toString()
            val pass = passwordInput.text.toString()
            val confirmPassword = passwordInput2.text.toString()

            // check pass
            if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(activity, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
                return
            }

            if (pass != confirmPassword) {
                Toast.makeText(activity, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                    .show()
                return
            }

            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(LoginFragment.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    token = FirebaseAuth.getInstance().currentUser!!.getIdToken(true).toString()
                    Log.e("token","$token")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(LoginFragment.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
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
            navigateAction.userId=currentUser.uid
            findNavController().navigate(navigateAction)

            val mHomeFragment = HomeFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.container, mHomeFragment, HomeFragment::class.java.simpleName)
//                addToBackStack(null)
                setReorderingAllowed(true)
                commit()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

}