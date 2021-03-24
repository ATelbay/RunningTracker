package com.smqpro.whyareyourunningtracker.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smqpro.whyareyourunningtracker.model.db.Run
import com.smqpro.whyareyourunningtracker.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val runListSortedByDate = mainRepository.getAllRunsSortedByDate()

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }
}