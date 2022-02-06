package com.example.messenger.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.messenger.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val username = usernameEt.text.toString()
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            registerBtn.setOnClickListener {
                Log.d("RegisterActivity", "creds: $username, $email, $password")
            }

            needAccountTv.setOnClickListener {
                Log.d("RegisterActivity", "Go to login")
            }
        }
    }
}