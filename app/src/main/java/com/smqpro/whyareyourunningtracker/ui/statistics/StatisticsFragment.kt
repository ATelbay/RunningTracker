package com.smqpro.whyareyourunningtracker.ui.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.smqpro.whyareyourunningtracker.R
import com.smqpro.whyareyourunningtracker.databinding.FragmentRunBinding
import com.smqpro.whyareyourunningtracker.databinding.FragmentSettingsBinding
import com.smqpro.whyareyourunningtracker.databinding.FragmentStatisticsBinding
import com.smqpro.whyareyourunningtracker.ui.main.MainViewModel
import com.smqpro.whyareyourunningtracker.ui.other.CustomMarkerView
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
        setViews()
        setObservers()
    }

    private fun setViews() = binding.apply {
        setBarChart()
    }

    private fun setBarChart() = binding.barChart.apply {
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        axisLeft.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        axisRight.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        description.text = "Avg speed over time"
        legend.isEnabled = false
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

        viewModel.runsSortedByDate.observe(viewLifecycleOwner) {
            it?.let {
                val allAvgSpeed = it.indices.map { i -> BarEntry(i.toFloat(), it[i].avgSpeedInKMH) }
                val barDataSet = BarDataSet(allAvgSpeed, "Avg speed over time").apply {
                    valueTextColor = Color.WHITE
                    color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
                }
                barChart.data = BarData(barDataSet)
                barChart.marker = CustomMarkerView(it.reversed(), requireContext(), R.layout.marker_view)
                barChart.invalidate()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}