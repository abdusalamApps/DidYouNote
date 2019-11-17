package com.halabware.didyounote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halabware.didyounote.database.Note
import com.halabware.didyounote.database.NoteDao
import kotlinx.coroutines.*

class NoteDetailsViewModel(dataSource: NoteDao) : ViewModel() {
    val database = dataSource
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToNotes = MutableLiveData<Boolean>()
    val navigateToNotes: LiveData<Boolean>
        get() = _navigateToNotes

    fun onSave(id: Long, text: String) {
        uiScope.launch {
            val note = Note(id = id, text = text)
            updateNote(note)
            _navigateToNotes.value = true
        }
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