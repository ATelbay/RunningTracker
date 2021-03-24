package com.smqpro.whyareyourunningtracker.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.smqpro.whyareyourunningtracker.databinding.ItemRunBinding
import com.smqpro.whyareyourunningtracker.model.db.Run
import com.smqpro.whyareyourunningtracker.utility.TrackingUtility
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Run>() {

        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return RunViewHolder(
            ItemRunBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            this
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RunViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Run>) {
        differ.submitList(list)
    }

    class RunViewHolder
    constructor(
        private val binding: ItemRunBinding,
        private val adapter: RunAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Run) = with(binding) {
            itemView.setOnClickListener {
                adapter.itemSelected?.let { it(item) }
            }

            Glide.with(root).load(item.img).into(ivRunImage)

            val calendar = Calendar.getInstance().apply {
                timeInMillis = item.timestamp
            }

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            tvDate.text = dateFormat.format(calendar.time)

            val avgSpeed = "${item.avgSpeedInKMH}km/h"
            tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${item.distanceInMeters / 1000}km"
            tvDistance.text = distanceInKm

            val time = TrackingUtility.getFormattedStopWatchTime(item.timeInMillis)
            tvTime.text = time

            val caloriesBurned = "${item.caloriesBurned}kcal"
            tvCalories.text = caloriesBurned
        }
    }

    private var itemSelected: ((Run) -> Unit)? = null
    fun setOnItemSelectedListener(listener: (Run) -> Unit) {
        itemSelected = listener
    }


}

