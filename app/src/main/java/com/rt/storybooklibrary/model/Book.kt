package com.rt.storybooklibrary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "summary") val summary: String?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "tags") val tags: String?,
    @ColumnInfo(name = "favorite") var favorite: Boolean = false

)
