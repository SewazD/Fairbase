package com.sewazd.fairbase


import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Проверка на авторизованность
        val auth = FirebaseAuth.getInstance()
        val email = TextEmailAddress.text
        val password = TextPassword.text
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
            if(email.isNotEmpty() || password.isNotEmpty()){
            val auth = FirebaseAuth.getInstance()
            if (auth.currentUser != null ) {
                val dataIntent = Intent(this, DataActivity::class.java)
                startActivity(dataIntent)
            } else {
                auth.signInWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val dataIntent = Intent(this, DataActivity::class.java)
                            startActivity(dataIntent)
                        }
                        else{
                            Toast.makeText(applicationContext, "Пожалуйста введите корректные данные",Toast.LENGTH_SHORT).show()
                        }
                    }


            }
            }
            else{
                Toast.makeText(applicationContext, "Заполните данные",Toast.LENGTH_SHORT).show()
            }
        }

        passwordReset.setOnClickListener{
            if(email.isNotEmpty()){
                Firebase.auth.sendPasswordResetEmail(email.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(applicationContext, "Отправлено на $email",Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else{
                Toast.makeText(applicationContext, "Введите корректный email",Toast.LENGTH_LONG).show()
            }
        }

        }



}




