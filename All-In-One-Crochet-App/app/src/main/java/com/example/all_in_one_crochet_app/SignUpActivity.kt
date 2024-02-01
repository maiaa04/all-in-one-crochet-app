package com.example.all_in_one_crochet_app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.all_in_one_crochet_app.firestore.FirestoreClass
import com.example.all_in_one_crochet_app.firestore.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : Snackbar() {

    private var nameEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var repeatPasswordEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()


        nameEditText = findViewById<EditText>(R.id.name_edit)
        emailEditText = findViewById<EditText>(R.id.email_edit)
        passwordEditText = findViewById<EditText>(R.id.password_edit)
        repeatPasswordEditText = findViewById<EditText>(R.id.repeat_password_edit)

        val signUpButton = findViewById<Button>(R.id.sign_up_button)

        signUpButton?.setOnClickListener{
            validateRegisterDetails()
            registerUser()

        }
//        signUpButton.setOnClickListener{
//            val intent = Intent(this, RowCounterOnlyActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
        val signInButton = findViewById<Button>(R.id.go_to_sign_in_button)
        signInButton.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateRegisterDetails(): Boolean {

        return when{
            TextUtils.isEmpty(emailEditText?.text.toString().trim{ it <= ' '}) -> {
                showSnackBar("Please enter your email",false)
                false
            }

            TextUtils.isEmpty(nameEditText?.text.toString().trim{ it <= ' '}) -> {
                showSnackBar("Please enter your name",false)
                false
            }

            TextUtils.isEmpty(passwordEditText?.text.toString().trim{ it <= ' '}) -> {
                showSnackBar("Please enter your password",false)
                false
            }

            TextUtils.isEmpty(repeatPasswordEditText?.text.toString().trim{ it <= ' '}) -> {
                showSnackBar("Please confirm your password",false)
                false
            }

            passwordEditText?.text.toString().trim {it <= ' '} != repeatPasswordEditText?.text.toString().trim{it <= ' '} -> {
                showSnackBar("Passwords mismatch",false)
                false
            }


            else -> {
//                showSnackBar("You are registered successfully",true)
                true
            }
        }


    }


    private fun registerUser(){
        if (validateRegisterDetails()){
            val login: String = emailEditText?.text.toString().trim() {it <= ' '}
            val password: String = passwordEditText?.text.toString().trim() {it <= ' '}
            val name: String = nameEditText?.text.toString().trim() {it <= ' '}



            FirebaseAuth.getInstance().createUserWithEmailAndPassword(login,password).addOnCompleteListener(
                OnCompleteListener <AuthResult>{ task ->

                    if(task.isSuccessful){
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        showSnackBar("You are registered successfully. Your user id is ${firebaseUser.uid}",true)

                        val user = User(login,
                            name,
                            true,
                            login,
                        )
                        FirestoreClass().registerUserFS(this@SignUpActivity, user)



                        FirebaseAuth.getInstance().signOut()
                        finish()


                    } else{
                        showSnackBar(task.exception!!.message.toString(),false)
                    }

                }
            )

        }
    }

    fun  userRegistrationSuccess(){

        Toast.makeText(this@SignUpActivity, resources.getString(R.string.register_successfully),
            Toast.LENGTH_LONG).show()
    }
}