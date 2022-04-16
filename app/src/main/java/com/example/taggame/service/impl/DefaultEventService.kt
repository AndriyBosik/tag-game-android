package com.example.taggame.service.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.taggame.service.EventService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultEventService<T> @Inject constructor() : EventService<T> {
    private val event = MutableLiveData<T>()

    override fun publish(data: T) {
        event.postValue(data!!)
    }

    override fun subscribe(observer: Observer<T>) {
        event.observeForever(observer)
    }

    override fun unsubscribe(observer: Observer<T>) {
        event.removeObserver(observer)
    }
}