package com.halabware.didyounote

import android.text.Editable
import android.text.TextWatcher

fun formatSearchQueryString(searchQuery: String): String {
    return "%$searchQuery%"
}

class myTextWatcher: TextWatcher{
    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}