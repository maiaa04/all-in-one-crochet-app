package com.example.all_in_one_crochet_app

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.all_in_one_crochet_app.firestore.FirestoreData
import com.example.all_in_one_crochet_app.firestore.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

open class SettingsActivity : Snackbar() {

    val db = Firebase.firestore
    @SuppressLint("SuspiciousIndentadion")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // trying shit
        val intent = intent
        var name = ""
        var email = ""
        var formattedDateTime: String = ""
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val nameText = findViewById<TextView>(R.id.name_text)
        val emailText = findViewById<TextView>(R.id.email_text)

        GlobalScope.launch(Dispatchers.IO){
            userId?.let{
                val documentSnapshot = db.collection("users")
                    .document(FirebaseAuth.getInstance().currentUser?.email.toString())
                    .get().await()
                if (documentSnapshot.exists()){
                    val dbData = documentSnapshot.toObject(User::class.java)
                    name = dbData?.name.toString()
                    email = dbData?.email.toString()

                    nameText.text = name
                    emailText.text = email
                }
            }
        }

        val profilePicture = findViewById<ImageView>(R.id.profile_pic)
        val accountDetailsButton = findViewById<MaterialButton>(R.id.account_details_button)
        val themesButton = findViewById<MaterialButton>(R.id.themes_button)
//            val systemTheme = findViewById<RadioButton>(R.id.sys_def_button)
            val lightTheme = findViewById<RadioButton>(R.id.light_theme_button)
            val darkTheme = findViewById<RadioButton>(R.id.dark_theme_button)
        val termsButton = findViewById<MaterialButton>(R.id.terms_n_conditions_button)
        val privacyButton = findViewById<MaterialButton>(R.id.privacy_policy_button)
        val contactButton = findViewById<MaterialButton>(R.id.contact_us_button)
        val signOutButton = findViewById<MaterialButton>(R.id.sign_out_button)

        val darkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = darkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

        if (isDarkModeOn){
            darkTheme.isChecked
        } else {
            lightTheme.isChecked
        }
        lightTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        darkTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        signOutButton.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}