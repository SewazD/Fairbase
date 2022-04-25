package com.sewazd.fairbase

import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration.*
import java.text.DateFormat

class RegistrationActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val db = Firebase.firestore
        RegistrationButton.setOnClickListener {
            val email = regEmail.text.toString()
            val password = regPassword.text.toString()
            val nick = regName.text.toString()
            val password_check = regPassword2.text.toString()

            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if(password.length >= 9 && password == password_check   && nick.length >= 6 && nick.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val auth = FirebaseAuth.getInstance().currentUser
                            val selectedDate = calendarView.date
                            val calendar = Calendar.getInstance()
                            calendar.timeInMillis = selectedDate
                            val dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT)
                            val user = hashMapOf(
                                "email" to "$email",
                                "nickname" to "$nick",
                                "born" to "${dateFormatter.format(calendar.time)}",
                            )
                            db.collection("users").document("${auth?.email}")
                                .set(user)
                                .addOnSuccessListener {
                                    //TODO перевести на новую стрницу
                                    val dataIntent = Intent(this, DataActivity::class.java)
                                    startActivity(dataIntent)
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Error adding document", e)
                                }
                        } else {
                            textView.text = "error"
                            Toast.makeText(applicationContext, "Введите корректные данные",Toast.LENGTH_LONG).show()
                        }
                    }
            }
                else{
                    if(password.length < 9) {
                        Toast.makeText(applicationContext, "Длина пароля не менее 9 символов",Toast.LENGTH_LONG).show()
                    }
                    if(password != password_check){
                        Toast.makeText(applicationContext, "Пароли не совпадают",Toast.LENGTH_LONG).show()
                    }
                    if(nick.isEmpty()){
                        Toast.makeText(applicationContext, "Введите корректно имя",Toast.LENGTH_LONG).show()
                    }
                    if(nick.length < 6){
                        Toast.makeText(applicationContext, "Длина имени не менее 6 символов",Toast.LENGTH_LONG).show()
                    }
                }

            }
        else {
            Toast.makeText(applicationContext, "Введите корректный email",Toast.LENGTH_LONG).show()
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