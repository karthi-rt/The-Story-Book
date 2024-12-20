package com.rt.storybooklibrary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rt.storybooklibrary.model.Book
import com.rt.storybooklibrary.data.BookDatabase
import com.rt.storybooklibrary.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel (application: Application): AndroidViewModel(application) {

    val readAllStory : LiveData<List<Book>>
    private val repository: BookRepository

    init {
        val bookDao = BookDatabase.getDatabase(application).bookDao()
        repository = BookRepository(bookDao)
        readAllStory = repository.readAllStory
    }

    fun addBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStory(book)
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateStory(book)
        }
    }

    fun getBookById(id: Int): LiveData<Book> {
        return repository.getBookById(id)
    }

    fun deleteStory(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteStory(book)
        }
    }

    fun updateFavoriteStatus(bookId: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteStatus(bookId, isFavorite)
        }
    }

    fun getFavoriteBooks(): LiveData<List<Book>> {
        return repository.getFavoriteBooks()
    }

//    fun deleteAllStory() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteAllStory()
//        }
//    }

}

        /*
        viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.addStory(book)
                } catch (e: Exception) {
                    Log.e("BookViewModel", "Error adding book: ${e.message}")
                }
            }
        */