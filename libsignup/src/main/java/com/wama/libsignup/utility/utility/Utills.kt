package com.wama.libsignup.utility.utility

import android.view.View
import com.google.android.material.snackbar.Snackbar

class Utills {
    fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}