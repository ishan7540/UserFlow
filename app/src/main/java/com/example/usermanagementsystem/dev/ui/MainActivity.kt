package com.example.usermanagementsystem.dev.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.usermanagementsystem.databinding.ActivityMainBinding
import com.example.usermanagementsystem.dev.core.viewmodel.AuthViewModel
import com.example.usermanagementsystem.dev.core.viewmodel.NoteViewModel
import com.example.usermanagementsystem.dev.core.viewmodel.NoteViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteViewModel = ViewModelProvider(
            this,
            NoteViewModelFactory(application) )[NoteViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }


        binding.showPassword.setOnClickListener {
            val isVisible = binding.etPassword.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            if (isVisible) {
                binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                //binding.showPassword.setImageResource(R.drawable.eye_slash_visibility_visible_hide_hidden_show_watch_svgrepo_com)

            } else {
                binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            }
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }


        binding.btnSubmit.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            authViewModel.login(email, password)
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }



        authViewModel.authUser.observe(this) { user ->
            if (user != null) {
                noteViewModel.setSession(user.email, user.name)
                startActivity(Intent(this, HomeScreen::class.java))
                finish()
            }
        }

        authViewModel.authStatus.observe(this) { result ->
            when (result) {
                "Wrong password" -> Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show()
                "Account does not exist" -> Toast.makeText(this, "Account doesn't exist", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
