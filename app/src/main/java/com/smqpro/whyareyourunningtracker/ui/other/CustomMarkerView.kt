package com.smqpro.whyareyourunningtracker.ui.other

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.smqpro.whyareyourunningtracker.R
import com.smqpro.whyareyourunningtracker.databinding.MarkerViewBinding
import com.smqpro.whyareyourunningtracker.model.db.Run
import com.smqpro.whyareyourunningtracker.utility.TrackingUtility
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView(
    val runList: List<Run>,
    context: Context,
    layoutId: Int
) : MarkerView(context, layoutId) {

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        e?.let {
            val curRunId = e.x.toInt()
            val run = runList[curRunId]
            
            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timestamp
            }

            val tvDate = findViewById<TextView>(R.id.tvDate)
            val tvAvgSpeed = findViewById<TextView>(R.id.tvAvgSpeed)
            val tvDistance = findViewById<TextView>(R.id.tvDistance)
            val tvTime = findViewById<TextView>(R.id.tvDuration)
            val tvCalories = findViewById<TextView>(R.id.tvCaloriesBurned)

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            tvDate.text = dateFormat.format(calendar.time)

            val avgSpeed = "${run.avgSpeedInKMH}km/h"
            tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${run.distanceInMeters / 1000}km"
            tvDistance.text = distanceInKm

            val time = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)
            tvTime.text = time

            val caloriesBurned = "${run.caloriesBurned}kcal"
            tvCalories.text = caloriesBurned
        }
    }
}