package com.example.phi.extensions

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.phi.R

fun Context.setAlertDialog(): Dialog {
    val builder = AlertDialog.Builder(this)
    builder.setView(R.layout.dialog)
    val dialog: Dialog = builder.create()
    dialog.setCancelable(false)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    return dialog
}