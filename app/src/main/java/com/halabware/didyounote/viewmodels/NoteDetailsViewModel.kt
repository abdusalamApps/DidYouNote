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

    private val _navigateToEditor = MutableLiveData<Boolean>()
    val navigateToEditor: LiveData<Boolean>
        get() = _navigateToEditor

    fun doneNavigatingToEditor() {
        _navigateToEditor.value = false
    }

    private var note = MutableLiveData<Note?>()

    init {

    }


    fun initializeNote(id: Long) {
        uiScope.launch {
            note.value = getByIdFromDatabase(id)
        }
    }

    private suspend fun getByIdFromDatabase(id: Long): Note? {
        return withContext(Dispatchers.IO) {
            val note = database.getById(id)
            note
        }
    }
}