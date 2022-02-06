package com.example.messenger.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.messenger.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            loginBtn.setOnClickListener {
                Log.d("LoginActivity", "creds: $email, $password")
            }

            needAccountTv.setOnClickListener {
                Log.d("LoginActivity", "Go to register")
            }
        }
    }
}