package com.c22ps099.relasiahelpseekerapp.ui.main

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.SessionPreferences
import com.c22ps099.relasiahelpseekerapp.databinding.ActivityMainBinding


internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<SessionViewModel> {
        SessionViewModel.Factory(SessionPreferences.getInstance(dataStore))
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        supportActionBar?.hide()

        viewModel.getToken().observe(this) {
            token = it
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController

        binding?.bottomNavigation?.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.homeFragment || destination.id == R.id.accountFragment|| destination.id ==R.id.missionFragment|| destination.id==R.id.bookmarkFragment) {

                binding?.bottomNavigation?.visibility  =View.VISIBLE
            } else {

                binding?.bottomNavigation?.visibility  = View.GONE
            }
        }
        binding?.bottomNavigation?.setOnItemSelectedListener {item ->
            onNavDestinationSelected(item, Navigation.findNavController(this, R.id.container))
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