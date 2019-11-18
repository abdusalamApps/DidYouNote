package com.halabware.didyounote.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Query("select * from notes_table order by date desc")
    fun getAllNotes(): LiveData<List<Note>>

    @Update
    fun update(note: Note)

    @Query("delete from notes_table where noteId == :id")
    fun delete(id: Long)

    @Query("select * from notes_table where noteId == :id")
    fun getById(id: Long): Note?

    @Query("select * from notes_table where text like :query ")
    fun searchByText(query: String): LiveData<List<Note>>

}