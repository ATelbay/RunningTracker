package com.smqpro.whyareyourunningtracker.ui.run

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smqpro.whyareyourunningtracker.R
import com.smqpro.whyareyourunningtracker.databinding.FragmentRunBinding
import com.smqpro.whyareyourunningtracker.ui.main.MainViewModel


class RunFragment : Fragment() {
    private var _binding: FragmentRunBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRunBinding.inflate(inflater, container, false)

        setUi()

        return binding.root
    }

    private fun setUi() = binding.apply {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}