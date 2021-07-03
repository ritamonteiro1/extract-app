package com.example.phi.utils

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.phi.R

class Utils {
    companion object {
        fun setAlertDialog(context: Context): Dialog {
            val builder = AlertDialog.Builder(context)
            builder.setView(R.layout.dialog)
            val dialog: Dialog = builder.create()
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            return dialog
        }
    }
}