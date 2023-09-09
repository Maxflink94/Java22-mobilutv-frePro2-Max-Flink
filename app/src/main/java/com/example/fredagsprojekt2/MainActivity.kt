package com.example.fredagsprojekt2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore

        val loginBtn : Button = findViewById(R.id.logInBtn)
        val regBtn : Button = findViewById(R.id.regBtn)

        val enterEmail : EditText = findViewById(R.id.username)
        val enterPass : EditText = findViewById(R.id.enterPass)

        loginBtn.setOnClickListener {
            val email = enterEmail.text.toString()
            val password = enterPass.text.toString()

            db.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val i = Intent(this@MainActivity, MainActivity2::class.java)
                        i.putExtra("email", email)
                        i.putExtra("password", password)

                        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("email", email)
                        editor.apply()

                        startActivity(i)
                    }else{
                        Log.d("Authentication", "User not found or password Incorrect")
                        Toast.makeText(this@MainActivity, "Fel Email och/eller lösenord", Toast.LENGTH_LONG).show()
                    }
                }
        }

        regBtn.setOnClickListener {
            val i = Intent(this@MainActivity, MainActivity4::class.java)
            startActivity(i)
        }
    }
}