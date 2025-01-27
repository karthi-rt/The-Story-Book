package com.rt.storybooklibrary.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rt.storybooklibrary.R
import com.rt.storybooklibrary.activity.add.AddStoryActivity
import com.rt.storybooklibrary.activity.list.BookAdapter
import com.rt.storybooklibrary.databinding.ActivityMainBinding
import com.rt.storybooklibrary.model.Book
import com.rt.storybooklibrary.viewmodel.BookViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mBookViewModel: BookViewModel
    private lateinit var adapter: BookAdapter
    private var isFavoriteFilterActive: Boolean = false // Tracks the filter state

    // Lists to hold all books and favorite books
    private var allBooksList: List<Book> = emptyList()
    private var favoriteBooksList: List<Book> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = BookAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        // Observe all books once
        mBookViewModel.readAllStory.observe(this) { allBooks ->
            allBooksList = allBooks
            updateRecyclerView()
        }

        // Observe favorite books once
        mBookViewModel.getFavoriteBooks().observe(this) { favoriteBooks ->
            favoriteBooksList = favoriteBooks
            if (isFavoriteFilterActive) {
                updateRecyclerView()
            }
        }

//        // Observe all stories initially
//        mBookViewModel.readAllStory.observe(this) { allBooks ->
//            if (!isFavoriteFilterActive) {
//                updateRecyclerView(allBooks, "Add your story") // Default message for all stories
//            }
//        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }

        binding.ivFavourite.setOnClickListener {
            isFavoriteFilterActive = !isFavoriteFilterActive
            updateRecyclerView()
        }
    }

    override fun onResume() {
        super.onResume()
        // Update the RecyclerView when returning to MainActivity
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        val currentList = if (isFavoriteFilterActive) {
            favoriteBooksList
        } else {
            allBooksList
        }

        if (currentList.isEmpty()) {
            val emptyMessage = if (isFavoriteFilterActive) "Add a favorite story to your collection!" else "Begin your storytelling journey by adding your story to the library!"
            binding.tvStories.text = emptyMessage
            binding.tvStories.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.tvStories.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            adapter.setData(currentList)
        }

        // Update the favorite icon
        binding.ivFavourite.setImageResource(
            if (isFavoriteFilterActive) R.drawable.ic_star_filled else R.drawable.ic_star_outline
        )
    }
}