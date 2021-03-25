package com.smqpro.whyareyourunningtracker.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.smqpro.whyareyourunningtracker.databinding.FragmentSettingsBinding
import com.smqpro.whyareyourunningtracker.ui.main.MainActivity
import com.smqpro.whyareyourunningtracker.utility.Constants.KEY_NAME
import com.smqpro.whyareyourunningtracker.utility.Constants.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sp: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        setUi()

        return binding.root
    }

    private fun setUi() {
        loadFieldsFromSharedPreferences()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnApplyChanges.setOnClickListener {

            val success = applyChangesToSharedPreferences()
            if (success) {
                Snackbar.make(view, "Saved changes", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, "Please fill out all the fields", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun loadFieldsFromSharedPreferences() = binding.apply {
        val name = sp.getString(KEY_NAME, "")
        val weight = sp.getFloat(KEY_WEIGHT, 80F)
        etName.setText(name)
        etWeight.setText(weight.toString())

    }

    private fun applyChangesToSharedPreferences(): Boolean {
        binding.apply {

            val name = etName.text.toString()
            val weight = etWeight.text.toString()
            if (name.isEmpty() || weight.isEmpty()) {
                return false
            }
            sp.edit()
                .putString(KEY_NAME, name)
                .putFloat(KEY_WEIGHT, weight.toFloat())
                .apply()
            val toolbarText = "Let's go, $name!"
            (requireActivity() as MainActivity).setToolbarTitle(toolbarText)
            return true
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}