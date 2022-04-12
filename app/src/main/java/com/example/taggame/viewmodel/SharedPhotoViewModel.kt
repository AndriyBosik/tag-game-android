package com.example.taggame.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taggame.meta.NavigationEventType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SharedPhotoViewModel @Inject constructor(): ViewModel() {
    private val mutableNavigationEvent: MutableLiveData<NavigationEventType> = MutableLiveData()
    val navigationEvent: LiveData<NavigationEventType> = mutableNavigationEvent

    private val mutablePhoto: MutableLiveData<Uri> = MutableLiveData()
    val photo: LiveData<Uri> = mutablePhoto

    fun photoSelected(photoPath: Uri) {
        mutablePhoto.value = photoPath
        mutableNavigationEvent.postValue(NavigationEventType.START)
    }
}