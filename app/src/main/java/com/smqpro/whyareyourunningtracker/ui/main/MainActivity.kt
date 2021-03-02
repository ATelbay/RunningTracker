package com.smqpro.whyareyourunningtracker.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.smqpro.whyareyourunningtracker.R
import com.smqpro.whyareyourunningtracker.databinding.ActivityMainBinding
import com.smqpro.whyareyourunningtracker.db.RunDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setUi()


    }


    private fun setUi() = binding.apply {
        setContentView(root)
        setSupportActionBar(toolbar)

        val navHostFragment = nav_host_fragment as NavHostFragment

        bottomNavigationView.setupWithNavController(navHostFragment.navController)

        navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->

            when(destination.id) {
                R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                } else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }

        }
    }
}