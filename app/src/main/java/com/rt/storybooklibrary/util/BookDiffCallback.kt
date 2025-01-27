package com.rt.storybooklibrary.util

import androidx.recyclerview.widget.DiffUtil
import com.rt.storybooklibrary.model.Book

class BookDiffCallback(
    private val oldList: List<Book>,
    private val newList: List<Book>
): DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}