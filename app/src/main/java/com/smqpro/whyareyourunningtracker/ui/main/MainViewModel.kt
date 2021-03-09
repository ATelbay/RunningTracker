package com.smqpro.whyareyourunningtracker.ui.main

import androidx.lifecycle.ViewModel
import com.smqpro.whyareyourunningtracker.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {

}