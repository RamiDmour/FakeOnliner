package com.example.fakeonliner.components

import android.app.AlertDialog
import android.content.Context
import com.example.fakeonliner.R

class LoadingDialog(private val context: Context) {
    private val dialog: AlertDialog = AlertDialog.Builder(this.context)
        .setView(R.layout.loading_dialog)
        .setCancelable(false)
        .create()

    fun startLoadingDialog() {
        dialog.show()
    }

    fun dismissDialog() {
        dialog.hide()
    }
}
