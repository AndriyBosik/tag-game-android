package com.example.taggame.viewmodel

import androidx.lifecycle.ViewModel
import com.example.taggame.model.FieldSize
import com.example.taggame.service.EventService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedFieldSizeViewModel @Inject constructor(
    private val recordFieldSizeEventService: EventService<FieldSize>
): ViewModel() {
    fun fieldSizeSelected(fieldSize: Int) {
        recordFieldSizeEventService.publish(FieldSize(fieldSize))
    }
}