package com.example.taggame.service

import com.example.taggame.model.FieldSize
import com.example.taggame.model.Time

interface RecordService {
    suspend fun saveRecord(time: Time, fieldSize: Int)

    suspend fun getFieldSizesWithRecords(): List<Int>

    suspend fun getRecordsByFieldSize(fieldSize: FieldSize): List<Time>
}