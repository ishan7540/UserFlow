package com.example.usermanagementsystem.dev.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.usermanagementsystem.R
import com.example.usermanagementsystem.databinding.FragmentProfileBinding
import com.example.usermanagementsystem.databinding.FragmentUsersBinding
import com.example.usermanagementsystem.dev.core.viewmodel.NoteViewModel
import com.example.usermanagementsystem.dev.ui.MainActivity
import kotlinx.coroutines.MainScope

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentUsersBinding
    val viewModel: NoteViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)

        val prefs = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val name = prefs.getString("name", null)
        viewModel.chatMessages.observe(viewLifecycleOwner){ chatMessages ->
            binding.statTotalChatNumber.text = chatMessages.size.toString()
        }

        viewModel.allNotes.observe(viewLifecycleOwner){ notes ->
            binding.statTotalNotesNumber.text = notes.size.toString()
        }
        binding.logoutText.setOnClickListener {
            val editor = prefs.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding.name.setText(name)
        binding.email.setText(email)

        return binding.root
    }
}