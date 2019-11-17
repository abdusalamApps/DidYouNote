package com.halabware.didyounote

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import java.lang.StringBuilder

@BindingAdapter("app:editDateText")
fun setEditDateText(view: TextView, date: String) {
    view.text = "Edited $date"
}

@BindingAdapter("app:shortNoteText")
fun setShortNoteText(view: TextView, text: String) {
    if (text.length > 60) {
        var stringBuilder = StringBuilder()
        for (i in 0..60) {
            stringBuilder.append(text[i])
        }
        stringBuilder.append("...")
        view.text = stringBuilder.toString()
    } else {
        view.text = text
    }
}