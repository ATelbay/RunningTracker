package com.smqpro.whyareyourunningtracker.ui.setup

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.smqpro.whyareyourunningtracker.R
import com.smqpro.whyareyourunningtracker.databinding.FragmentSetupBinding
import com.smqpro.whyareyourunningtracker.ui.main.MainActivity
import com.smqpro.whyareyourunningtracker.utility.Constants.KEY_FIRST_TIME_TOGGLE
import com.smqpro.whyareyourunningtracker.utility.Constants.KEY_NAME
import com.smqpro.whyareyourunningtracker.utility.Constants.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment() {
    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sp: SharedPreferences

    @set:Inject
    var isFirstAppOpen = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetupBinding.inflate(inflater, container, false)
        setNavController(savedInstanceState)

        setUi()

        return binding.root
    }

    private fun setNavController(savedInstanceState: Bundle?) {
        if (!isFirstAppOpen) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment, true)
                .build()
            findNavController().navigate(R.id.action_setupFragment_to_runFragment, savedInstanceState, navOptions)
        }
    }

    private fun setUi() = binding.apply {


        tvContinue.setOnClickListener {
            val success = writePersonalDataToSharedPref()
            if (success)
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            else
                Snackbar
                    .make(root, "Please enter all the fields", Snackbar.LENGTH_SHORT)
                    .show()
        }
    }

    private fun writePersonalDataToSharedPref(): Boolean {
        binding.apply {
            val name = etName.text.toString()
            val weight = etWeight.text.toString()
            if (name.isEmpty() || weight.isEmpty()) {
                return false
            }
            sp.edit()
                .putString(KEY_NAME, name)
                .putFloat(KEY_WEIGHT, weight.toFloat())
                .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
                .apply()

            val toolbarText = "Let's go, $name"
            (requireActivity() as MainActivity).setToolbarTitle(toolbarText)
            return true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}