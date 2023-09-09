package com.example.fredagsprojekt2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val spinner: Spinner = findViewById(R.id.spinner)
        val spinnerArray = resources.getStringArray(R.array.spinnerArray)

        val defaultText = "Välj ett alternativ"
        val itemList = mutableListOf(defaultText)
        itemList.addAll(spinnerArray)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val selectedItem = parent.getItemAtPosition(pos).toString()
        when (selectedItem) {
            "Visa min information (Funkar ej ÄN)" -> {

                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val loggedInEmail = sharedPreferences.getString("email", "")

                val db = Firebase.firestore

                db.collection("users")
                    .whereEqualTo("email", loggedInEmail)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty) {
                            val userDocument = querySnapshot.documents[0] // Antag att det finns en matchande användare

                            val fullName = userDocument.getString("fullname")
                            val email = userDocument.getString("email")
                            val date = userDocument.getLong("birthdate")
                            val sex = userDocument.getString("sex")
                            val driverLicense = userDocument.getBoolean("driverlicense")

                            val showFullName: TextView = findViewById(R.id.showFullname)
                            val showEmail: TextView = findViewById(R.id.showEmail)
                            val showDate: TextView = findViewById(R.id.showDate)
                            val showSex: TextView = findViewById(R.id.showSex)
                            val showDriverLicense: TextView = findViewById(R.id.showDriverLicense)

                            showFullName.text = "Fullständigt namn: ${fullName}"
                            showEmail.text = "Email: ${email}"
                            showDate.text = "Födelsedatum: ${date}"
                            showSex.text = "Kön: ${sex}"
                            showDriverLicense.text = "Körkort: ${if (driverLicense == true) "Ja" else "Nej"}"
                        } else {
                            Toast.makeText(this@MainActivity2, "Problem med att hämta info", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@MainActivity2, "Problem med att hämta info", Toast.LENGTH_LONG).show()
                    }
            }
            "Logga ut" -> {
                val i = Intent(this@MainActivity2, MainActivity::class.java)
                startActivity(i)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }
}