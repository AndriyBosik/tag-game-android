package com.example.taggame.viewmodel

import androidx.lifecycle.*
import com.example.taggame.model.FieldSize
import com.example.taggame.model.Time
import com.example.taggame.service.EventService
import com.example.taggame.service.RecordService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val recordFieldSizeEventService: EventService<FieldSize>,
    private val recordService: RecordService
): ViewModel() {
    private val mutableRecords = MutableLiveData<List<Time>>(listOf())
    val records: LiveData<List<Time>> = mutableRecords

    private val recordsFetcher = object: Observer<FieldSize> {
        override fun onChanged(data: FieldSize) {
            viewModelScope.launch {
                mutableRecords.value = recordService.getRecordsByFieldSize(data)
            }
            recordFieldSizeEventService.unsubscribe(this)
        }
    }

    init {
        recordFieldSizeEventService.subscribe(recordsFetcher)
    }
}