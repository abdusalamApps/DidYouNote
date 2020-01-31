package com.halabware.didyounote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halabware.didyounote.database.Note
import com.halabware.didyounote.database.NoteDao
import com.halabware.didyounote.formatSearchQueryString
import java.util.*

class SearchViewModel(dataSource: NoteDao) : ViewModel() {

    val database = dataSource

    fun search(searchQuery: CharSequence): LiveData<List<Note>> {
        return database.searchByText(formatSearchQueryString(searchQuery.toString()))
    }

    var notes = search("?")


}