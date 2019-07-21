package com.nadarm.boardmvvmrx.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

inline fun EditText.addTextChanged(crossinline function: (string: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val value = s.toString()
            function(value)
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })

}