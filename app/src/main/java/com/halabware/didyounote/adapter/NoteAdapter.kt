package com.halabware.didyounote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.halabware.didyounote.database.Note
import com.halabware.didyounote.databinding.NoteListItemLayoutBinding

class NoteAdapter(val clickListener: NoteClickListener): ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallBack()) {

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    class NoteViewHolder private constructor(val binding: NoteListItemLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note, clickListener: NoteClickListener) {
            binding.note = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NoteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    NoteListItemLayoutBinding.inflate(layoutInflater, parent, false)
                return NoteViewHolder(binding)
            }
        }
    }

}