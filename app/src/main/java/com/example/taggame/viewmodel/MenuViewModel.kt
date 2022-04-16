package com.example.taggame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taggame.meta.DialogEventType
import com.example.taggame.meta.NavigationEventType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {
    private val mutableNavigationEvent: MutableLiveData<NavigationEventType> = MutableLiveData()
    val navigationEvent: LiveData<NavigationEventType> = mutableNavigationEvent

    private val mutableDialogEvent: MutableLiveData<DialogEventType> = MutableLiveData()
    val dialogEvent: LiveData<DialogEventType> = mutableDialogEvent

    fun selectPhotoClicked() {
        mutableDialogEvent.postValue(DialogEventType.SELECT_PHOTO)
    }

    fun recordsClicked() {
        mutableNavigationEvent.postValue(NavigationEventType.RECORDS)
    }
}