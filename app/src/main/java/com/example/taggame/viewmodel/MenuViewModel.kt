package com.example.taggame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taggame.meta.DialogEventType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {
    private val mutableDialogEvent: MutableLiveData<DialogEventType> = MutableLiveData()
    val dialogEvent: LiveData<DialogEventType> = mutableDialogEvent

    fun selectPhotoClicked() {
        mutableDialogEvent.postValue(DialogEventType.SELECT_PHOTO)
    }
}