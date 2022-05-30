package com.c22ps099.relasiahelperapp.ui.main

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.c22ps099.relasiahelperapp.R
import com.c22ps099.relasiahelperapp.data.SessionPreferences
import com.c22ps099.relasiahelperapp.databinding.ActivityBaseBinding

internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class BaseActivity : AppCompatActivity() {
    private val viewModel by viewModels<SessionViewModel> {
        SessionViewModel.Factory(SessionPreferences.getInstance(dataStore))
    }

    private var _binding: ActivityBaseBinding? = null
    private val binding get() = _binding

    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        _binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel.getToken().observe(this) {
            token = it
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding?.navView?.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment) {
                binding?.navView?.visibility = View.GONE
            } else {
                binding?.navView?.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}