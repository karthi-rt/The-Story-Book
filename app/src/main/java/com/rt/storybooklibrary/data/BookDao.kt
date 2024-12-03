package com.rt.storybooklibrary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rt.storybooklibrary.model.Book

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStory(book: Book)

    @Update
    suspend fun updateStory(book: Book)

    @Delete
    suspend fun deleteStory(book: Book)

    @Query("SELECT * FROM books ORDER BY id ASC")
    fun readAllStory(): LiveData<List<Book>>

    @Query("DELETE FROM books")
    suspend fun deleteAllStory()

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById(id: Int): LiveData<Book>

}