package com.example.taggame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taggame.service.RecordService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FieldSizeListViewModel @Inject constructor(
    private val recordService: RecordService
): ViewModel() {
    private val mutableFieldSizes = MutableLiveData<List<Int>>(mutableListOf())
    val fieldSizes: LiveData<List<Int>> = mutableFieldSizes

    init {
        viewModelScope.launch {
            mutableFieldSizes.value = recordService.getFieldSizesWithRecords()
        }
    }
}