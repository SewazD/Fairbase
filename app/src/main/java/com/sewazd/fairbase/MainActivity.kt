package com.sewazd.fairbase


import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Проверка на авторизованность
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            textView.text = "Logined"
            val dataIntent = Intent(this, DataActivity::class.java)
            startActivity(dataIntent)
        }
        //Проверка на авторизованность
        buttonReg.setOnClickListener() {
            textView.text = "Registring"
            val regIntent = Intent(this, RegistrationActivity::class.java)
            startActivity(regIntent)
        }
        buttonLogin.setOnClickListener {
            if(TextEmailAddress.text != null && TextPassword.text != null){
            val auth = FirebaseAuth.getInstance()
            if (auth.currentUser != null) {
                val dataIntent = Intent(this, DataActivity::class.java)
                startActivity(dataIntent)
            } else {
                auth.signInWithEmailAndPassword(TextEmailAddress.text.toString(),
                    TextPassword.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val dataIntent = Intent(this, DataActivity::class.java)
                            startActivity(dataIntent)
                        }
                        else{
                            textView.text = "Введите корректные данные"
                        }
                    }


            }
            }
            else{
                textView.text = "Введите корректные данные"
            }
        }
        }}


