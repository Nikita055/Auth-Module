package com.wama.libsignup.utility.utility

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.wama.libsignup.R
import java.io.IOException
import java.nio.charset.StandardCharsets

class Utills {
    fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
    fun getJsonFromAssets(context: Context, fileName: String?): String? {
        return try {
            val `is` = context.assets.open(fileName!!)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    fun showAlertDialog(activity: Activity?, title: String?, msg: String?, buttonTitle: String?) {
        val builder = MaterialAlertDialogBuilder(activity!!, R.style.RoundShapeTheme)
        builder.setTitle(title)
            .setMessage(msg)
            .setPositiveButton(buttonTitle) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            .show()
    }



}