package com.example.taggame.module

import com.example.taggame.service.GameDataEventService
import com.example.taggame.service.GameService
import com.example.taggame.service.impl.DefaultGameDataEventService
import com.example.taggame.service.impl.DefaultGameService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewModelModule {
    @Binds
    abstract fun bindGameService(gameService: DefaultGameService): GameService

    @Binds
    abstract fun bindFieldSizeEventService(fieldSizeEventService: DefaultGameDataEventService): GameDataEventService
}