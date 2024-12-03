package com.rt.storybooklibrary.activity.add

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.rt.storybooklibrary.R
import com.rt.storybooklibrary.model.Book
import com.rt.storybooklibrary.viewmodel.BookViewModel
import com.rt.storybooklibrary.databinding.ActivityAddStoryBinding

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var mBookViewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        binding.ivBack.setOnClickListener { finish() }

        binding.ivAdd.setOnClickListener { insertDataToDatabase() }
    }

    private fun insertDataToDatabase() {
        val title = binding.etTitle.text.toString().trim()
        val summary = binding.etSummary.text.toString().trim()
        val category = binding.etCategory.text.toString().trim()
        val tags = binding.etTags.text.toString().trim()
        val author = binding.etAuthor.text.toString().trim()

        if (inputCheck(title, summary, category, author, tags)) {
            // Create User Object
            val book = Book(0, title, summary, category, author, tags)
            // Add Data to Database
            mBookViewModel.addBook(book)
            Snackbar.make(binding.root, "Successfully added!", Snackbar.LENGTH_LONG).show()
            clearForm()
            finish()
        } else {
            Snackbar.make(binding.root, "Please fill out all fields.", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(title: String, summary: String, category: String, author: String, tags: String): Boolean {
        return title.isNotEmpty() && summary.isNotEmpty() && category.isNotEmpty() && author.isNotEmpty() && tags.isNotEmpty()
    }

    private fun clearForm() {
        binding.etTitle.text?.clear()
        binding.etSummary.text?.clear()
        binding.etCategory.text?.clear()
        binding.etTags.text?.clear()
        binding.etAuthor.text?.clear()
    }

}