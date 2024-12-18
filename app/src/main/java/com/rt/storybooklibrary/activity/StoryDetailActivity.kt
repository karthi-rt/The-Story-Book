package com.rt.storybooklibrary.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.rt.storybooklibrary.R
import com.rt.storybooklibrary.activity.update.UpdateStoryActivity
import com.rt.storybooklibrary.databinding.ActivityStoryDetailBinding
import com.rt.storybooklibrary.viewmodel.BookViewModel

class StoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryDetailBinding
    private lateinit var mBookViewModel: BookViewModel
    private var bookId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize ViewModel
        mBookViewModel = ViewModelProvider(this)[BookViewModel::class.java]

        // Retrieve story ID from the Intent
        bookId = intent.getIntExtra("id", -1)
        if (bookId == -1) {
            finish() // Close activity if no valid ID is provided
            return
        }

        // Observe and update UI with story details
        observeStoryDetails()

        binding.ivEdit.setOnClickListener { navigateToUpdateStoryActivity() }

        binding.ivBack.setOnClickListener { finish() }

        binding.ivDelete.setOnClickListener { deleteStory() }
    }

    private fun observeStoryDetails() {
        mBookViewModel.getBookById(bookId).observe(this) { book ->
            if (book != null) {
                binding.tvTitle.text = book.title
                binding.tvSummary.text = book.summary
                binding.tvCategory.text = book.category
                binding.tvAuthor.text = book.author
                binding.tvTags.text = book.tags
            } else {
                // Handle the case where the book is not found
                binding.tvTitle.text = "N/A"
                binding.tvSummary.text = "N/A"
                binding.tvCategory.text = "N/A"
                binding.tvAuthor.text = "N/A"
                binding.tvTags.text = "N/A"
            }
        }
    }

    private fun navigateToUpdateStoryActivity() {
        val intent = Intent(this, UpdateStoryActivity::class.java).apply {
            putExtra("updateId", bookId)
            putExtra("updateTitle", binding.tvTitle.text.toString())
            putExtra("updateSummary", binding.tvSummary.text.toString())
            putExtra("updateCategory", binding.tvCategory.text.toString())
            putExtra("updateAuthor", binding.tvAuthor.text.toString())
            putExtra("updateTags", binding.tvTags.text.toString())
        }
        startActivity(intent)
    }

//   Log.d("StoryDetailActivity", "id: $id, title: ${tvTitle.text}, summary: ${tvSummary.text}," + " category: ${tvCategory.text}, author: ${tvAuthor.text}, tags: ${tvTags.text}")

    private fun deleteStory() {
        mBookViewModel.getBookById(bookId).observe(this) { book ->
            book?.let {
                val builder = AlertDialog.Builder(this)
                builder.setPositiveButton("Yes") { _, _ ->
                    // Call ViewModel's deleteStory method with the Book object
                    mBookViewModel.deleteStory(it)
                    Snackbar.make(binding.root, "Successfully removed: ${it.title} Story", Snackbar.LENGTH_LONG).show()
                    finish() // Close the activity
                }
                builder.setNegativeButton("No") { _, _ -> }
                builder.setTitle("Delete ${it.title}?")
                builder.setMessage("Are you sure you want to delete ${it.title}? Story")
                builder.create().show()
            }
        }
    }
}