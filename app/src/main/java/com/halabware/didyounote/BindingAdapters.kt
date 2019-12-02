package com.halabware.didyounote

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import java.lang.StringBuilder

@BindingAdapter("app:editDateText")
fun setEditDateText(view: TextView, date: String) {
    if (date != "") {
        view.text = "Edited $date"
    }
}

@BindingAdapter("app:shortNoteText")
fun setShortNoteText(view: TextView, text: String) {
    if (text.length > 60) {
        val stringBuilder = StringBuilder()
        for (i in 0..60) {
            stringBuilder.append(text[i])
        }
        stringBuilder.append("...")
        view.text = stringBuilder.toString()
    } else {
        view.text = text
    }
}

@BindingAdapter("app:onTextChanged")
fun onTextChanged(view: AppCompatEditText, searchFunction: (String) -> Unit) {
    view.addTextChangedListener(object: TextWatcher{
        override fun afterTextChanged(p0: Editable?) {
            searchFunction(view.text.toString().trim())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    })
}


