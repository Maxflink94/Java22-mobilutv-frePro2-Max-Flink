package com.example.fredagsprojekt2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val db = Firebase.firestore

        val backBtn: Button = findViewById(R.id.backButton)
        val subBtn: Button = findViewById(R.id.subBtn)

        subBtn.setOnClickListener {

            val newNameEditText: EditText = findViewById(R.id.newName)
            val fullName : String = newNameEditText.text.toString()

            val newEmailEditText: EditText = findViewById(R.id.newEmail)
            val email : String = newEmailEditText.text.toString()

            val newPassEditText: EditText = findViewById(R.id.newPass)
            val password : String = newPassEditText.text.toString()

            val newDateEditText: EditText = findViewById(R.id.newDate)
            val date: String = newDateEditText.text.toString()

            val radioChoice: RadioGroup = findViewById(R.id.radioChoice)
            val selectedRadioButtonId: Int = radioChoice.checkedRadioButtonId
            var sex = ""

            val checkBox: CheckBox = findViewById(R.id.checkBox)
            val hasDriverLicense: Boolean = checkBox.isChecked
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || date.isEmpty()){
                Toast.makeText(this@MainActivity4, "Fyll i alla fält", Toast.LENGTH_LONG).show()
            } else if (selectedRadioButtonId != -1) {
                val selectedRadioButton: RadioButton = findViewById(selectedRadioButtonId)
                sex = selectedRadioButton.text.toString()

                val user = hashMapOf(
                    "fullname" to fullName,
                    "email" to email,
                    "password" to password,
                    "birthdate" to date,
                    "sex" to sex,
                    "driverlicense" to hasDriverLicense
                )

                db.collection("users").add(user)

                val i = Intent(this@MainActivity4, MainActivity::class.java)
                startActivity(i)

                Toast.makeText(this@MainActivity4, "Användaren skapad", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this@MainActivity4, "Fyll i alla fält", Toast.LENGTH_LONG).show()
            }
        }

        backBtn.setOnClickListener {
            val i = Intent(this@MainActivity4, MainActivity::class.java)
            startActivity(i)
        }
    }
}