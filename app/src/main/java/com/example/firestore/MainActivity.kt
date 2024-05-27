package com.example.firestore

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firestore.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var db: Firebase
    private lateinit var binding: ActivityMainBinding
    private  val TAG = "MainActivity"
    private lateinit var list: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Firebase.firestore
        list=ArrayList()
        binding.apply {
            saveBtn.setOnClickListener {
                val name = name.text.toString()
                val age = age.text.toString().toInt()
                val email = email.text.toString()
                val user = User(name, age, email)
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference->
                        Log.d(TAG, "${documentReference}")
                    }.addOnFailureListener {
                        Log.d(TAG, "$it")
                    }
            }
            readBtn.setOnClickListener {

                db.collection("users")
                    .get()
                    .addOnSuccessListener { result ->
                       result.forEach{queryDocumentSnapshot->
                          val user=queryDocumentSnapshot.toObject(User::class.java)

                           list.add(user)
                       }
                    }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error getting documents.", exception)
                    }
                readText.text = list.toString()
            }
        }
    }
}