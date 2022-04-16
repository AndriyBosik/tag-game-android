package com.example.taggame.config

import com.example.taggame.mapper.RecordMapper
import com.example.taggame.mapper.impl.DefaultRecordMapper
import com.example.taggame.model.FieldSize
import com.example.taggame.model.GameData
import com.example.taggame.service.EventService
import com.example.taggame.service.GameService
import com.example.taggame.service.RecordService
import com.example.taggame.service.impl.DefaultEventService
import com.example.taggame.service.impl.DefaultGameService
import com.example.taggame.service.impl.DefaultRecordService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SingletonModule {
    @Binds
    abstract fun bindGameService(gameService: DefaultGameService): GameService

    @Binds
    abstract fun bindFieldSizeEventService(fieldSizeEventService: DefaultEventService<GameData>): EventService<GameData>

    @Binds
    abstract fun bindRecordFieldSizeEventService(recordFieldSizeEventService: DefaultEventService<FieldSize>): EventService<FieldSize>

    @Binds
    abstract fun bindRecordMapper(recordMapper: DefaultRecordMapper): RecordMapper

    @Binds
    abstract fun bindRecordService(recordService: DefaultRecordService): RecordService
}