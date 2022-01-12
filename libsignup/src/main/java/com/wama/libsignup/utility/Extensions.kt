package com.wama.libsignup.utility

import android.annotation.SuppressLint
import android.util.Patterns
import android.view.View
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
