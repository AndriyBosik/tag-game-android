package com.example.taggame.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taggame.meta.NavigationEventType
import com.example.taggame.model.GameData
import com.example.taggame.service.GameDataEventService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SharedPhotoViewModel @Inject constructor(
    private val gameDataEventService: GameDataEventService
): ViewModel() {
    private val mutableNavigationEvent: MutableLiveData<NavigationEventType> = MutableLiveData()
    val navigationEvent: LiveData<NavigationEventType> = mutableNavigationEvent

    val fieldSize = MutableLiveData("4")

    fun photoSelected(photo: Bitmap) {
        gameDataEventService.publish(GameData(
            Integer.parseInt(fieldSize.value!!),
            photo))
        mutableNavigationEvent.postValue(NavigationEventType.START)
    }
}