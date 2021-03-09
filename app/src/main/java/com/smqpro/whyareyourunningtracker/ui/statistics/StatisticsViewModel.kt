package com.smqpro.whyareyourunningtracker.ui.statistics

import androidx.lifecycle.ViewModel
import com.smqpro.whyareyourunningtracker.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {


}