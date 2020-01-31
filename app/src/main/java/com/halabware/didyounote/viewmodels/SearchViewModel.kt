package com.halabware.didyounote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halabware.didyounote.database.Note
import com.halabware.didyounote.database.NoteDao
import com.halabware.didyounote.formatSearchQueryString

class SearchViewModel(dataSource: NoteDao) : ViewModel() {

    val database = dataSource

    public fun search(query: String): LiveData<List<Note>> {
        return database.searchByText(formatSearchQueryString(query))
    }

    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word


    fun changeWord(new: String) {
        _word.value = new
    }

}