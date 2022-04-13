package com.example.taggame.module

import com.example.taggame.service.GameService
import com.example.taggame.service.impl.DefaultGameService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun bindGameService(gameService: DefaultGameService): GameService
}