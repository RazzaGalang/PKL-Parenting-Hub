package com.example.pklparentinghub.utils

import android.text.Editable
import android.text.TextWatcher

interface SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(text: Editable?) {}
}
