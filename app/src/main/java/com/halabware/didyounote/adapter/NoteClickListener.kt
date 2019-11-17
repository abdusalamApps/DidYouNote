package com.halabware.didyounote.adapter

import com.halabware.didyounote.database.Note

class NoteClickListener(val clickListener: (text: String) -> Unit) {
    fun onClick(note: Note) = clickListener(note.text)
}