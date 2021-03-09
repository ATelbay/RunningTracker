package com.smqpro.whyareyourunningtracker.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.smqpro.whyareyourunningtracker.R
import com.smqpro.whyareyourunningtracker.databinding.ActivityMainBinding
import com.smqpro.whyareyourunningtracker.utility.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navHostFrag: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        navigateToTrackingFragment(intent)

        setUi()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragment(intent)
    }


    private fun setUi() = binding.apply {
        setContentView(root)
        setSupportActionBar(toolbar)

        navHostFrag.findNavController().addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }

        }
    }

    private fun navigateToTrackingFragment(intent: Intent?) {
        if (intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            navHostFrag.findNavController().navigate(R.id.action_global_to_trackingFragment)
        }
    }
}