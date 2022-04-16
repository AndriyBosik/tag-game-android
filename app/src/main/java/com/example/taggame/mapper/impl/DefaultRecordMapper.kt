package com.example.taggame.mapper.impl

import com.example.taggame.entity.RecordEntity
import com.example.taggame.mapper.RecordMapper
import com.example.taggame.model.Time
import javax.inject.Inject

class DefaultRecordMapper @Inject constructor(): RecordMapper {
    override fun toEntity(time: Time, fieldSize: Int): RecordEntity {
        return RecordEntity(
            null,
            fieldSize,
            time.minutes,
            time.seconds
        )
    }

    override fun toModel(entity: RecordEntity): Time {
        return Time(
            entity.minutes,
            entity.seconds
        )
    }
}