package com.example.taggame.service.impl

import com.example.taggame.dao.RecordDao
import com.example.taggame.mapper.RecordMapper
import com.example.taggame.model.FieldSize
import com.example.taggame.model.Time
import com.example.taggame.service.RecordService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultRecordService @Inject constructor(
    private val recordMapper: RecordMapper,
    private val recordDao: RecordDao
): RecordService {
    override suspend fun saveRecord(time: Time, fieldSize: Int) {
        recordDao.insert(recordMapper.toEntity(time, fieldSize))
    }

    override suspend fun getFieldSizesWithRecords(): List<Int> {
        return recordDao.getFieldSizesDistinct()
    }

    override suspend fun getRecordsByFieldSize(fieldSize: FieldSize): List<Time> {
        return recordDao.getAllByFieldSize(fieldSize.value)
            .map { recordMapper.toModel(it) }
    }
}