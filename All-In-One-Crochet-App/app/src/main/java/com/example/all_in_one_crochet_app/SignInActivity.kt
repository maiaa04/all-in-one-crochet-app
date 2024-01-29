package com.example.all_in_one_crochet_app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : Snackbar() {

    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var signInButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()


        emailEditText = findViewById<EditText>(R.id.email_edit)
//        emailEditText.background.alpha = 70
        passwordEditText = findViewById<EditText>(R.id.password_edit)
//        passwordEditText.background.alpha = 70

        signInButton = findViewById<Button>(R.id.sign_in_button)
        signInButton?.setOnClickListener{
            logInRegisteredUser()
        }

        val signUpButton = findViewById<Button>(R.id.go_to_sign_up_button)
        signUpButton.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        val breakInButton = findViewById<Button>(R.id.break_in_button)
        breakInButton.setOnClickListener{
            val intent = Intent(this, RowCounterOnlyActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun validateLoginDetails(): Boolean {

        return when{
            TextUtils.isEmpty(emailEditText?.text.toString().trim{ it <= ' '}) -> {
                showSnackBar("Please enter your email", false)
                false
            }

            TextUtils.isEmpty(passwordEditText?.text.toString().trim{ it <= ' '}) -> {
                showSnackBar("Please enter your password",false)
                false
            }

            else -> {
//                showSnackBar("Your details are valid",true)
                true
            }
        }


    }

    private fun logInRegisteredUser(){


        if(validateLoginDetails()){
            val email = emailEditText?.text.toString().trim(){ it<= ' '}
            val password = passwordEditText?.text.toString().trim(){ it<= ' '}

            //Log-in using FirebaseAuth

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{task ->

                    if(task.isSuccessful){
                        showSnackBar("You are logged in successfully.", false)
                        goToRowCounterActivity()
                        finish()

                    } else{
                        showSnackBar(task.exception!!.message.toString(),true)
                    }
                }
        }
    }

    open fun goToRowCounterActivity() {

        val user = FirebaseAuth.getInstance().currentUser;
        val uid = user?.email.toString()

        val intent = Intent(this, RowCounterOnlyActivity::class.java)
        intent.putExtra("uID",uid)
        startActivity(intent)
    }
}