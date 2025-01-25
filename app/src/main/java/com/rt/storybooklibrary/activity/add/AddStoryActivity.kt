package com.rt.storybooklibrary.activity.add

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.rt.storybooklibrary.R
import com.rt.storybooklibrary.databinding.ActivityAddStoryBinding
import com.rt.storybooklibrary.model.Book
import com.rt.storybooklibrary.viewmodel.BookViewModel

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

        binding.ivBack.setOnClickListener {
            if (isFormNotEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Discard changes?")
                    .setMessage("You have unsaved changes. Are you sure you want to exit?")
                    .setPositiveButton("Yes") { _, _ -> finish() }
                    .setNegativeButton("No", null)
                    .show()
            } else {
                finish()
            }
        }

        binding.ivAdd.setOnClickListener { insertDataToDatabase() }
    }

    private fun insertDataToDatabase() {

        binding.progressCyclic.visibility = View.VISIBLE

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
            binding.progressCyclic.visibility = View.GONE
            Snackbar.make(binding.root, "Successfully added!", Snackbar.LENGTH_LONG).show()
            clearForm()
            finish()
        } else {
            binding.progressCyclic.visibility = View.GONE
            Snackbar.make(binding.root, "Please fill out all fields.", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(title: String, summary: String, category: String, author: String, tags: String): Boolean {
        return title.isNotEmpty() && summary.isNotEmpty() && category.isNotEmpty() && author.isNotEmpty() && tags.isNotEmpty()
        /*
        var isValid = true

        if (title.isEmpty()) {
            binding.etTitle.error = "Title cannot be empty"
            isValid = false
        }
        if (summary.isEmpty()) {
            binding.etSummary.error = "Summary cannot be empty"
            isValid = false
        }
        // Repeat for other fields...
        return isValid
        ----------------------------------------------
        binding.etTitle.addTextChangedListener {
            binding.etTitle.error = null
        }
        // Repeat for other fields...

         */
    }

    private fun clearForm() {
        binding.etTitle.text?.clear()
        binding.etSummary.text?.clear()
        binding.etCategory.text?.clear()
        binding.etTags.text?.clear()
        binding.etAuthor.text?.clear()
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        if (isFormNotEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Discard changes?")
                .setMessage("You have unsaved changes. Are you sure you want to exit?")
                .setPositiveButton("Yes") { _, _ -> super.onBackPressed() }
                .setNegativeButton("No", null)
                .show()
        } else {
            super.onBackPressed()
        }
    }

    private fun isFormNotEmpty(): Boolean {
        return binding.etTitle.text?.isNotEmpty() ?: false ||
                binding.etSummary.text?.isNotEmpty() ?: false ||
                binding.etCategory.text?.isNotEmpty() ?: false ||
                binding.etTags.text?.isNotEmpty() ?: false ||
                binding.etAuthor.text?.isNotEmpty() ?: false
    }

}