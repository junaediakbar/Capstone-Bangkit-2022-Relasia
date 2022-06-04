package com.c22ps099.relasiahelperapp.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.databinding.FragmentLoginBinding
import com.c22ps099.relasiahelperapp.ui.home.HomeFragment
import com.c22ps099.relasiahelperapp.ui.register.RegisterFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    companion object {
        private const val TAG = "LoginFragment"
        private const val WEB_CLIENT_ID =
            "269202247329-u5ad2hpor327e69ku1fjbjklksffdm50.apps.googleusercontent.com"
    }

    private lateinit var loginViewModel: LoginViewModel
    private var binding: FragmentLoginBinding? = null
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
            ViewModelProvider(this)[LoginViewModel::class.java]
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        googleAuth = Firebase.auth

        binding?.apply {
            ibGmail.setOnClickListener {
                signInGoogle()
            }
            tvRegister.setOnClickListener {
                val navigateAction = LoginFragmentDirections
                    .actionLoginFragmentToRegisterFragment()
                findNavController().navigate(navigateAction)

                val mRegisterFragment = RegisterFragment()
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment, mRegisterFragment, RegisterFragment::class.java.simpleName)
                    setReorderingAllowed(true)
                    commit()
                }
            }
        }

    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        googleAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = googleAuth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val navigateAction = LoginFragmentDirections
                .actionLoginFragmentToHomeFragment()
            navigateAction.token = "token"
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
        val currentUser = googleAuth.currentUser
        updateUI(currentUser)
    }
}