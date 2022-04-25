package com.sewazd.fairbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val db = Firebase.firestore
        val auth = FirebaseAuth.getInstance().currentUser
        buttonAddList.setOnClickListener {
            AddLayout.isVisible = true
            buttonAddList.isVisible = false
        }
        closeAddLayout.setOnClickListener{
            AddLayout.isVisible = false
            buttonAddList.isVisible = true
        }
        buttonApplayList.setOnClickListener(){
            val listName = ListName.text.toString()
            val listText = ListText.text.toString()
            val listDate = "22.12.23"
            val data = hashMapOf(
                "name" to listName,
                "text" to listText,
                "date" to listDate,
                "email" to auth?.email
            )
            db.collection("list")
                .document()
                .set(data)
                .addOnSuccessListener{
                    Toast.makeText(applicationContext, "Задача успешно создана", Toast.LENGTH_LONG).show()
                    AddLayout.isVisible = false
                    buttonAddList.isVisible = true
                }
                .addOnFailureListener{
                    Toast.makeText(applicationContext, "Произошла непредвиденная ошибка", Toast.LENGTH_LONG).show()
                }

        }


        db.collection("list").whereEqualTo("email", "${auth?.email}")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val array =arrayOf("${document.data["name"]}")
                    val array2 =arrayOf("${document.data["text"]}")
                    val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = CustomRecyclerAdapter(array.toList(),array2.toList())
                }
                }


            }
}

private operator fun Any.plusAssign(data: String) {

}











