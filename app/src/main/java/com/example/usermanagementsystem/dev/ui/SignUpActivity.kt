package com.example.usermanagementsystem.dev.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.usermanagementsystem.databinding.ActivitySignUp2Binding
import com.example.usermanagementsystem.dev.core.viewmodel.AuthViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUp2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUp2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val authViewModel: AuthViewModel by viewModels()

        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password == confirmPassword) {
                authViewModel.register(name, email, password)
            } else {
                Toast.makeText(this, "Please check all fields", Toast.LENGTH_SHORT).show()
            }
        }

        authViewModel.authStatus.observe(this) { result ->
            if (result == "success") {
                Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            }
        }


    }
    }
