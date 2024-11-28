package com.rt.storybooklibrary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStory(book: Book)

    @Delete
    suspend fun deleteStory(book: Book)

    @Update
    suspend fun updateStory(book: Book)

    @Query("SELECT * FROM books ORDER BY id ASC")
    fun readAllStory(): LiveData<List<Book>>
//    suspend fun readAllStory(): List<Book>
}