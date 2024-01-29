package com.example.all_in_one_crochet_app.firestore

import com.example.all_in_one_crochet_app.SignUpActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()


    fun registerUserFS(activity: SignUpActivity, userInfo: User){

        mFireStore.collection("users")
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()

            }
            .addOnFailureListener{

            }
    }
}