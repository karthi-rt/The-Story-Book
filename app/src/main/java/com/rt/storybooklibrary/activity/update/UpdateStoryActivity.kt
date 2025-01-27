package com.rt.storybooklibrary.activity.update

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.rt.storybooklibrary.R
import com.rt.storybooklibrary.activity.StoryDetailActivity
import com.rt.storybooklibrary.databinding.ActivityUpdateStoryBinding
import com.rt.storybooklibrary.model.Book
import com.rt.storybooklibrary.viewmodel.BookViewModel

class UpdateStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateStoryBinding
    private lateinit var mBookViewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        // Retrieve data from the Intent
        val id = intent.getIntExtra("updateId", -1)
        val title = intent.getStringExtra("updateTitle") ?: "N/A"
        val summary = intent.getStringExtra("updateSummary") ?: ""
        val category = intent.getStringExtra("updateCategory") ?: ""
        val author = intent.getStringExtra("updateAuthor") ?: ""
        val tags = intent.getStringExtra("updateTags") ?: ""

        Log.d("UpdateStoryActivity", "id: $id, title: $title, summary: $summary, category: $category, author: $author, tags: $tags")

        // Set data to the TextInputEditText views
        binding.etUpdateTitle.setText(title)
        binding.etUpdateSummary.setText(summary)
        binding.etUpdateCategory.setText(category)
        binding.etUpdateAuthor.setText(author)
        binding.etUpdateTags.setText(tags)

        binding.ivUpdate.setOnClickListener { updateStory() }

        binding.ivBack.setOnClickListener { finish() }
    }

    private fun updateStory(){
        // Here you would update the story using the new data (e.g., save to database or send back to previous activity)
        val updatedId = intent.getIntExtra("updateId", -1)
        val updatedTitle = binding.etUpdateTitle.text.toString()
        val updatedSummary = binding.etUpdateSummary.text.toString()
        val updatedCategory = binding.etUpdateCategory.text.toString()
        val updatedAuthor = binding.etUpdateAuthor.text.toString()
        val updatedTags = binding.etUpdateTags.text.toString()

        Log.d("UpdatedStory", "updatedTitle: $updatedTitle, updatedSummary: $updatedSummary, updatedCategory: $updatedCategory, updatedAuthor: $updatedAuthor, updatedTags: $updatedTags")

        if(inputCheck(updatedTitle, updatedSummary, updatedCategory, updatedAuthor, updatedTags)) {
            // Create Book Object
            val updatedBook = Book(updatedId, updatedTitle, updatedSummary, updatedCategory, updatedAuthor, updatedTags)
            // Update Current Story
            mBookViewModel.updateBook(updatedBook)
            Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show()

            // Navigate back to StoryDetailActivity
            val intent = Intent(this, StoryDetailActivity::class.java)
            intent.putExtra("storyId", updatedBook.id)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
            finish()
        } else {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(title: String, summary: String, category: String, author: String, tags: String): Boolean {
        return title.isNotEmpty() && summary.isNotEmpty() && category.isNotEmpty() && author.isNotEmpty() && tags.isNotEmpty()
    }

}