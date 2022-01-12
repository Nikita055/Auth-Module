package com.wama.libsignup.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.google.android.material.button.MaterialButton
import com.wama.libsignup.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

fun showSnackBar(view: View, msg: String) {
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
}

@SuppressLint("StringFormatInvalid")
fun TextInputEditText.validateEmail():Boolean{
    val emailId=text.toString()
    if (emailId!!.isEmpty()) {
        error = context.getString(R.string.email_empty_error,emailId)
        return false
    } else {
        val isValid = Patterns.EMAIL_ADDRESS.matcher(emailId!!).matches()
        if (!isValid) {
            error = "Invalid Email address"
            return false
        }
    }
    return true
}

@SuppressLint("StringFormatInvalid")
fun TextInputEditText.validatePassword():Boolean{
    val regexPassword = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,20}$"
    val password = text!!.toString().trim { it <= ' ' }
    if (password.isEmpty()) {
        error = context.getString(R.string.password_empty_error,password)
        return false
    } else if (!password.matches(regexPassword.toRegex())) {
        error = context.getString(R.string.password_invalid_error,password)
        return false
    }
    return true
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/*
this function will be use to observe onSubmit action click of keyboard
 */
fun TextInputEditText.onSubmit(func: () -> Unit) = setOnEditorActionListener { _, actionId, keyEvent ->

    if (actionId == EditorInfo.IME_ACTION_DONE
        || (keyEvent.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) {
        hideKeyboard()
        func()
        return@setOnEditorActionListener true
    }

    return@setOnEditorActionListener false
}

/*
this function will be use to attach textWatcher to TextInputEditText and return text afterTextChanged
 */
fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

/*
This extension function will be use for enable the material button
 */
fun MaterialButton.setEnable(){
    this.isEnabled=true
}

/*
This extension function will be use for disable the material button
 */
fun MaterialButton.setDisable(){
    this.isEnabled=false
}
