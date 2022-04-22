package com.sewazd.fairbase

import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration.*
import java.text.DateFormat

class RegistrationActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val db = Firebase.firestore
        RegistrationButton.setOnClickListener {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(regEmail.text.toString(),
                regPassword.text.toString())

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val auth = FirebaseAuth.getInstance().currentUser
                    val selectedDate = calendarView.date
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = selectedDate
                    val dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT)
                    val user = hashMapOf(
                        "email" to "${regEmail.text}",
                        "nickname" to "${regName.text}",
                        "born" to "${dateFormatter.format(calendar.time)}",
                    )
                    db.collection("users").document("${auth?.email}")
                        .set(user)
                        .addOnSuccessListener {
                            //TODO перевести на новую стрницу
                            val dataIntent = Intent(this, DataActivity::class.java)
                            startActivity(dataIntent)
                        }
                        .addOnFailureListener{e->
                            Log.w(TAG,"Error adding document",e)
                        }
                }
                else{
                    textView.text ="error"
                }
            }
    }
        buttonOpenCalendary.setOnClickListener {
            calendarLayout.isVisible= true
            buttonOpenCalendary.isVisible = false
        }
        buttonCloseCalendary.setOnClickListener{
            calendarLayout.isVisible= false
            buttonOpenCalendary.isVisible = true
        }
    }
}