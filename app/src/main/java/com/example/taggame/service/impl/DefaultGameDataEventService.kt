package com.example.taggame.service.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.taggame.model.GameData
import com.example.taggame.service.GameDataEventService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultGameDataEventService @Inject constructor() : GameDataEventService {
    private val gameDataEvent = MutableLiveData<GameData>()

    override fun publish(gameData: GameData) {
        gameDataEvent.postValue(gameData)
    }

    override fun subscribe(observer: Observer<GameData>) {
        gameDataEvent.observeForever(observer)
    }

    override fun unsubscribe(observer: Observer<GameData>) {
        gameDataEvent.removeObserver(observer)
    }
}