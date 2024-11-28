package com.rt.storybooklibrary.data

import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {

    val readAllStory: LiveData<List<Book>> = bookDao.readAllStory()

    suspend fun addStory(book: Book) {
        bookDao.addStory(book)
    }

    suspend fun deleteStory(book: Book) {
        bookDao.deleteStory(book)
    }
}