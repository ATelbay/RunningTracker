package com.smqpro.whyareyourunningtracker.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.smqpro.whyareyourunningtracker.databinding.FragmentRunBinding
import com.smqpro.whyareyourunningtracker.databinding.FragmentSettingsBinding
import com.smqpro.whyareyourunningtracker.databinding.FragmentStatisticsBinding
import com.smqpro.whyareyourunningtracker.ui.main.MainViewModel
import com.smqpro.whyareyourunningtracker.utility.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.round
import kotlin.math.roundToInt

@AndroidEntryPoint
class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatisticsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        setUi()

        return binding.root
    }

    private fun setUi() {
        setObservers()
    }

    private fun setObservers() = binding.apply {
        viewModel.totalTimeInMillis.observe(viewLifecycleOwner) {
            it?.let {
                val totalTimeRun = TrackingUtility.getFormattedStopWatchTime(it)
                tvTotalTime.text = totalTimeRun
            }
        }

        viewModel.totalDistance.observe(viewLifecycleOwner) {
            it?.let {
                val km = it / 1000
                val totalDistance = (km * 10f).roundToInt() / 10f
                val totalDistanceStr = "${totalDistance}km"
                tvTotalDistance.text = totalDistanceStr
            }
        }

        viewModel.totalAvgSpeed.observe(viewLifecycleOwner) {
            it?.let {
                val avgSpeed = (it * 10F) / 10f
                val avgSpeedString = "${avgSpeed}km"
                tvAverageSpeed.text = avgSpeedString
            }
        }

        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner) {
            it?.let {
                val totalCalories = "${it}kcal"
                tvTotalCalories.text = totalCalories

            }
        }

        viewModel.totalDistance.observe(viewLifecycleOwner) {
            it?.let {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}