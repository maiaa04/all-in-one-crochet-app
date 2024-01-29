package com.example.all_in_one_crochet_app

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

open class Snackbar : AppCompatActivity() {
    fun showSnackBar(message: String, boolean: Boolean){
        val snackBar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG).setAction("Action", null)
        snackBar.setTextColor(ContextCompat.getColor(this, R.color.green6))

        // boolean -> true = no error, green snackBar
        // boolean -> false = error, red snackBar

        if (boolean) {
            snackBar.setText(message)
            snackBar.setBackgroundTint(resources.getColor(R.color.green4))
            snackBar.show()
        } else {
            snackBar.setText(message)
            snackBar.setBackgroundTint(resources.getColor(R.color.light_red))
            snackBar.show()
        }
    }
}