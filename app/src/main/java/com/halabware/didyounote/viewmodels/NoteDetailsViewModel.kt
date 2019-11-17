package com.halabware.didyounote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halabware.didyounote.database.Note
import com.halabware.didyounote.database.NoteDao
import kotlinx.coroutines.*

class NoteDetailsViewModel(
    private val noteId: Long,
    val dataSource: NoteDao
) : ViewModel() {
    val database = dataSource
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        initializeNote()
    }

    private val _note = MutableLiveData<Note?>()
    val note: LiveData<Note?>
        get() = _note

    private fun initializeNote() {
        uiScope.launch {
            _note.value = getNoteFromDatabase(noteId)
        }
    }


    private suspend fun getNoteFromDatabase(noteId: Long): Note? {
        return withContext(Dispatchers.IO) {
            val note = database.getById(noteId)
            note
        }
    }

    private val _navigateToNotes = MutableLiveData<Boolean>()
    val navigateToNotes: LiveData<Boolean>
        get() = _navigateToNotes

    fun onSave(text: String, date: String) {
        if (!text.equals(note.value?.text)) {
            uiScope.launch {
                val editDate = Note.getDateString()
                val newNote = Note(
                    noteId = noteId,
                    text = text,
                    date = date,
                    editDate = editDate)
                updateNote(newNote)
            }
        }
        _navigateToNotes.value = true
    }

    private suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            database.update(note)
        }
    }

    fun doneNavigatingToNotes() {
        _navigateToNotes.value = false
    }

}