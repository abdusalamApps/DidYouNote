package com.halabware.didyounote.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.halabware.didyounote.database.NoteDao
import com.halabware.didyounote.viewmodels.SearchViewModel
import java.lang.IllegalArgumentException

class SearchViewModelFactory(private val dataSource: NoteDao) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(dataSource = dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}