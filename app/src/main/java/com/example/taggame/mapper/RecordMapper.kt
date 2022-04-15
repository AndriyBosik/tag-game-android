package com.example.taggame.mapper

import com.example.taggame.entity.RecordEntity
import com.example.taggame.model.Time

interface RecordMapper {
    fun toEntity(time: Time, fieldSize: Int): RecordEntity
}