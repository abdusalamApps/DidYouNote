package com.halabware.didyounote.adapter

import androidx.recyclerview.widget.DiffUtil
import com.halabware.didyounote.database.Note

class NoteDiffCallBack: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.noteId == newItem.noteId
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }


}