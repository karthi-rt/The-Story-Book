package com.rt.storybooklibrary.activity.list

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rt.storybooklibrary.R
import com.rt.storybooklibrary.model.Book
import com.rt.storybooklibrary.activity.StoryDetailActivity
import com.rt.storybooklibrary.databinding.CustomBookListBinding


class BookAdapter: RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    private var bookList = emptyList<Book>()

    class MyViewHolder(val binding: CustomBookListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bookList[position]
//        holder.itemView.findViewById<TextView>(R.id.tv_BookTitle).text = currentItem.title
//        holder.itemView.findViewById<TextView>(R.id.tv_BookSummary).text = currentItem.summary
        holder.binding.tvBookTitle.text = currentItem.title
        holder.binding.tvBookSummary.text = currentItem.summary

        holder.itemView.findViewById<LinearLayout>(R.id.ll_bookItem).setOnClickListener { view ->
            val intent = Intent(view.context, StoryDetailActivity::class.java)
            intent.putExtra("id", currentItem.id)
            intent.putExtra("title", currentItem.title)
            intent.putExtra("summary", currentItem.summary)
            intent.putExtra("category", currentItem.category)
            intent.putExtra("author", currentItem.author)
            intent.putExtra("tags", currentItem.tags)
            view.context.startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(book: List<Book>) {
        this.bookList = book
        notifyDataSetChanged()
    }
}