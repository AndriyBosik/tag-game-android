package com.example.taggame.service

import androidx.lifecycle.Observer
import com.example.taggame.model.GameData

interface GameDataEventService {
    fun publish(gameData: GameData)

    fun subscribe(observer: Observer<GameData>)

    fun unsubscribe(observer: Observer<GameData>)
}