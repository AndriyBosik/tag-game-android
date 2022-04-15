package com.example.taggame.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "field_size") var fieldSize: Int,
    @ColumnInfo(name = "minutes") var minutes: Int,
    @ColumnInfo(name = "seconds") var seconds: Int
)