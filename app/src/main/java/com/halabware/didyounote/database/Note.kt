package com.halabware.didyounote.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long = 0L,
    val text: String,
    val date: String = getDateString(),
    val editDate: String = ""
) {
    companion object {
        fun getDateString(): String {
            val date = Date()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return simpleDateFormat.format(date)

        }

    }
}