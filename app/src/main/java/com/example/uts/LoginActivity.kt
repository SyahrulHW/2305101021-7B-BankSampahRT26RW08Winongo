package com.example.uts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("BankSampahPrefs", Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        val etNama = findViewById<EditText>(R.id.etNama)
        val rbAdmin = findViewById<RadioButton>(R.id.rbAdmin)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val nama = etNama.text.toString().trim()
            if (nama.isEmpty()) {
                Toast.makeText(this, "Silakan masukkan nama atau nomor rumah", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val role = if (rbAdmin.isChecked) "Admin" else "Warga"

            sharedPref.edit().apply {
                putBoolean("isLoggedIn", true)
                putString("userName", nama)
                putString("userRole", role)
                apply()
            }

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}