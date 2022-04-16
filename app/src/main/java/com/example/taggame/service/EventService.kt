package com.example.taggame.service

import androidx.lifecycle.Observer

interface EventService<T> {
    fun publish(data: T)

    fun subscribe(observer: Observer<T>)

    fun unsubscribe(observer: Observer<T>)
}