package com.smqpro.whyareyourunningtracker.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smqpro.whyareyourunningtracker.model.db.Run
import com.smqpro.whyareyourunningtracker.model.enum.SortType
import com.smqpro.whyareyourunningtracker.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val runListSortedByDate = mainRepository.getAllRunsSortedByDate()
    private val runListSortedByDistance = mainRepository.getAllRunsSortedByDistance()
    private val runListSortedByCaloriesBurned = mainRepository.getAllRunsSortedByCaloriesBurned()
    private val runListSortedByTimeInMillis = mainRepository.getAllRunsSortedByTimeInMillis()
    private val runListSortedByAvgSpeed = mainRepository.getAllRunsSortedByAvgSpeed()

    val runList = MediatorLiveData<List<Run>>()

    var sortType = SortType.DATE

    init {
        runList.addSource(runListSortedByDate) {
            if (sortType == SortType.DATE) {
                it?.let { runList.value = it }
            }
        }
        runList.addSource(runListSortedByDistance) {
            if (sortType == SortType.DISTANCE) {
                it?.let { runList.value = it }
            }
        }
        runList.addSource(runListSortedByCaloriesBurned) {
            if (sortType == SortType.CALORIES_BURNED) {
                it?.let { runList.value = it }
            }
        }
        runList.addSource(runListSortedByTimeInMillis) {
            if (sortType == SortType.RUNNING_TIME) {
                it?.let { runList.value = it }
            }
        }
        runList.addSource(runListSortedByAvgSpeed) {
            if (sortType == SortType.AVG_SPEED) {
                it?.let { runList.value = it }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when(sortType) {
        SortType.DATE -> runListSortedByDate.value?.let { runList.value = it }
        SortType.DISTANCE -> runListSortedByDistance.value?.let { runList.value = it }
        SortType.CALORIES_BURNED -> runListSortedByCaloriesBurned.value?.let { runList.value = it }
        SortType.RUNNING_TIME -> runListSortedByTimeInMillis.value?.let { runList.value = it }
        SortType.AVG_SPEED -> runListSortedByAvgSpeed.value?.let { runList.value = it }
    }.also {
        this.sortType = sortType
    }

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }
}