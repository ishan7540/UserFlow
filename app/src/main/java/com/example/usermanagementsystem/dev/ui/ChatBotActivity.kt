package com.example.usermanagementsystem.dev.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usermanagementsystem.databinding.ActivityChatBotBinding
import com.example.usermanagementsystem.dev.core.adapters.ChatAdapter
import com.example.usermanagementsystem.dev.core.models.ChatMessage
import com.example.usermanagementsystem.dev.core.viewmodel.NoteViewModel

class ChatBotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBotBinding
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }
        setupRecyclerView()

        viewModel.chatMessages.observe(this) { messages ->
            adapter.setMessages(messages)
            binding.recyclerview.scrollToPosition(messages.size - 1)
        }

        binding.send.setOnClickListener {
            val question = binding.question.text.toString().trim()
            if (question.isNotEmpty()) {
                viewModel.sendMessage(question)
                binding.question.text.clear()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter(this, emptyList())
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = false
        layoutManager.stackFromEnd = true
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter = adapter
    }
}
