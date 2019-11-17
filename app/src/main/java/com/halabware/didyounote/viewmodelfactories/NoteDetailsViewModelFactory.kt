package com.halabware.didyounote.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.halabware.didyounote.database.NoteDao
import com.halabware.didyounote.viewmodels.NoteDetailsViewModel
import com.halabware.didyounote.viewmodels.NotesViewModel
import java.lang.IllegalArgumentException

class NoteDetailsViewModelFactory (private val dataSource: NoteDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteDetailsViewModel::class.java)) {
            return NoteDetailsViewModel(dataSource = dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}