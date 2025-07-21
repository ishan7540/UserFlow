package com.example.usermanagementsystem.dev.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.example.usermanagementsystem.R
import com.example.usermanagementsystem.databinding.ActivityChatBotBinding
import com.example.usermanagementsystem.databinding.ActivityMainBinding
import com.example.usermanagementsystem.dev.core.viewmodel.ChatViewModel
import com.example.usermanagementsystem.dev.core.viewmodel.NoteViewModel
import kotlin.getValue

class ChatBotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBotBinding
    private val viewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

    }
}