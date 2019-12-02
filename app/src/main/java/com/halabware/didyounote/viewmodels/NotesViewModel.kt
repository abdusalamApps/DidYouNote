package com.halabware.didyounote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halabware.didyounote.database.NoteDao
import com.halabware.didyounote.formatSearchQueryString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class NotesViewModel(dataSource: NoteDao) : ViewModel() {
    val database = dataSource
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var notes = database.getAllNotes()

    fun search(searchQuery: CharSequence) {
        notes = database.searchByText(formatSearchQueryString(searchQuery.toString()))
    }

    private val _navigateToEditor = MutableLiveData<Boolean>()
    val navigateToEditor: LiveData<Boolean>
        get() = _navigateToEditor

    fun onAdd() {
        _navigateToEditor.value = true
    }

    fun doneNavigatingToEditor() {
        _navigateToEditor.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}