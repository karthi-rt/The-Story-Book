package com.rt.storybooklibrary.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewMode(application: Application): AndroidViewModel(application) {

    private val readAllStory : LiveData<List<Book>>
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
}