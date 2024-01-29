package com.example.all_in_one_crochet_app

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.android.material.button.MaterialButton

open class SettingsActivity : Snackbar() {


    @SuppressLint("SuspiciousIndentadion")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // trying shit
        val intent = intent
        val name = intent.getStringExtra("NAME")
        val email = intent.getStringExtra("EMAIL")
        var formattedDateTime: String = ""

        val profilePicture = findViewById<ImageView>(R.id.profile_pic)
        val nameText = findViewById<TextView>(R.id.name_text)
        nameText.text = name
        val emailText = findViewById<TextView>(R.id.email_text)
        emailText.text = email
        val accountDetailsButton = findViewById<MaterialButton>(R.id.account_details_button)
        val themesButton = findViewById<MaterialButton>(R.id.themes_button)
//            val systemTheme = findViewById<RadioButton>(R.id.sys_def_button)
            val lightTheme = findViewById<RadioButton>(R.id.light_theme_button)
            val darkTheme = findViewById<RadioButton>(R.id.dark_theme_button)
        val termsButton = findViewById<MaterialButton>(R.id.terms_n_conditions_button)
        val privacyButton = findViewById<MaterialButton>(R.id.privacy_policy_button)
        val contactButton = findViewById<MaterialButton>(R.id.contact_us_button)
        val signOutButton = findViewById<MaterialButton>(R.id.sign_out_button)

        val DarkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = DarkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

        lightTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        darkTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)        }

    }
}