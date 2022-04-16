package com.example.taggame.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taggame.entity.RecordEntity

@Dao
interface RecordDao {
    @Query("SELECT DISTINCT field_size FROM records")
    suspend fun getFieldSizesDistinct(): List<Int>

    @Query("SELECT * FROM records WHERE field_size=:fieldSize ORDER BY minutes, seconds")
    suspend fun getAllByFieldSize(fieldSize: Int): List<RecordEntity>

    @Insert
    suspend fun insert(record: RecordEntity)
}