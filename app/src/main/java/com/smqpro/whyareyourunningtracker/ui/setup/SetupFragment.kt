package com.smqpro.whyareyourunningtracker.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.smqpro.whyareyourunningtracker.R
import com.smqpro.whyareyourunningtracker.databinding.FragmentRunBinding
import com.smqpro.whyareyourunningtracker.databinding.FragmentSetupBinding

class SetupFragment : Fragment() {
    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetupBinding.inflate(inflater, container, false)

        setUi()

        return binding.root
    }

    private fun setUi() = binding.apply {
        tvContinue.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_runFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}