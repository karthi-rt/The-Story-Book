package com.rt.storybooklibrary.repository

import androidx.lifecycle.LiveData
import com.rt.storybooklibrary.data.BookDao
import com.rt.storybooklibrary.model.Book

class BookRepository(private val bookDao: BookDao) {

    val readAllStory: LiveData<List<Book>> = bookDao.readAllStory()

    suspend fun addStory(book: Book) {
        bookDao.addStory(book)
    }

    suspend fun updateStory(book: Book) {
        bookDao.updateStory(book)
    }

    suspend fun deleteStory(book: Book) {
        bookDao.deleteStory(book)
    }

    fun getBookById(id: Int): LiveData<Book> {
        return bookDao.getBookById(id)
    }

    suspend fun deleteAllStory() {
        bookDao.deleteAllStory()
    }

    suspend fun updateFavoriteStatus(bookId: Int, isFavorite: Boolean) {
        bookDao.updateFavoriteStatus(bookId, isFavorite)
    }

    fun getFavoriteBooks(): LiveData<List<Book>> {
        return bookDao.getFavoriteBooks()
    }
}