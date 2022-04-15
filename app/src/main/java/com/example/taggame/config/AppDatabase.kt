package com.example.taggame.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taggame.dao.RecordDao
import com.example.taggame.entity.RecordEntity

@Database(
    version = 1,
    entities = [RecordEntity::class]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recordDao(): RecordDao
}