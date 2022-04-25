@file:Suppress("UNREACHABLE_CODE")

package com.sewazd.fairbase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.activity_main.*

class DataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        val db = Firebase.firestore
        val auth = FirebaseAuth.getInstance().currentUser
        if(auth == null){
            val loginIntent = Intent(this, MainActivity::class.java)
            startActivity(loginIntent)
        }
            db.collection("users")
                .whereEqualTo("email", "${auth?.email}")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val nick = document.data["nickname"]
                        val email = document.data["email"]
                        val born = document.data["born"]
                        nickText.text = "$nick"
                        emailText.text = "$email"
                        bornText.text = "$born"
                    }
                }
                .addOnFailureListener { exception ->
                    nickText.text = "Error getting documents: \", $exception"
                }

        }
    override  fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.exitButton ->{
                FirebaseAuth.getInstance().signOut();
                val loginIntent = Intent(this, MainActivity::class.java)
                startActivity(loginIntent)
                return true
            }
            R.id.listButton ->{
                val listIntent = Intent(this, ListActivity::class.java)
                startActivity(listIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)


    }
    }
