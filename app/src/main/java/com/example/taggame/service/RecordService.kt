package com.example.taggame.service

import com.example.taggame.model.Time

interface RecordService {
    suspend fun saveRecord(time: Time, fieldSize: Int)

    suspend fun getFieldSizesWithRecords(): List<Int>

    suspend fun getAllRecords(): List<Time>
}