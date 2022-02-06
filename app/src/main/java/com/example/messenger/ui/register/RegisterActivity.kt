package com.example.messenger.ui.register

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Images.Media.getBitmap
import android.widget.Toast
import androidx.activity.viewModels
import com.example.messenger.ui.main.MainActivity
import com.example.messenger.databinding.ActivityRegisterBinding
import com.example.messenger.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            picSelectBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)
            }

            registerBtn.setOnClickListener {
                registerUser()
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                // to return to home screen of device instead of auth activities
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            alreadyHaveAccountTv.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // Look at selected photo
            viewModel.selectedPhotoUri = data.data
            val bitmap = getBitmap(contentResolver, viewModel.selectedPhotoUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.apply {
                picSelectIv.setBackgroundDrawable(bitmapDrawable)
                picSelectBtn.alpha = 0f
            }
        }
    }

    private fun registerUser() {
        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()
        viewModel.username = binding.usernameEt.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this@RegisterActivity,
                "Please enter an email and password.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.register(email, password)
        }
    }
}