package com.halabware.didyounote.viewmodels

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halabware.didyounote.database.Note
import com.halabware.didyounote.database.NoteDao
import kotlinx.coroutines.*
import org.w3c.dom.Text

class EditorViewModel(dataSource: NoteDao) : ViewModel() {

    val database = dataSource
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToNotes = MutableLiveData<Boolean>()
    val navigateToNotes: LiveData<Boolean>
        get() = _navigateToNotes


    fun onSave(text: String) {
        if (!TextUtils.isEmpty(text)) {
            uiScope.launch {
                val note = Note(text = text)
                insert(note)
                _navigateToNotes.value = true
            }
        }
    }

    suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            database.insert(note)
        }
    }

    fun doneNavigatingToNotes() {
        _navigateToNotes.value = false
    }


}