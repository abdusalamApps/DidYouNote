package com.halabware.didyounote.adapter

import com.halabware.didyounote.database.Note

class NoteClickListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(note: Note) = clickListener(note.id)
}