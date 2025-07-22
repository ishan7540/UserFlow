package com.example.usermanagementsystem.dev.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usermanagementsystem.databinding.FragmentNotesBinding
import com.example.usermanagementsystem.dev.core.viewmodel.NoteViewModel
import com.example.usermanagementsystem.dev.ui.ChatBotActivity
import com.example.usermanagementsystem.dev.ui.adapters.NoteAdapter

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding


    private val viewModel: NoteViewModel by activityViewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupListeners()

        val email = viewModel.getSessionEmail()
        if (email != null) {
            viewModel.loadNotesForEmail(email)
        }

        setupObservers()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter(emptyList()) { note ->
            val action = NotesFragmentDirections.actionNotesFragmentToEditFragment(note)
            findNavController().navigate(action)
        }
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            adapter.setNotes(notes)
        }
    }

    private fun setupListeners() {
        binding.fab.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToAddFragment())
        }

        binding.chatbot.setOnClickListener {
            val intent = Intent(requireContext(), ChatBotActivity::class.java)
            startActivity(intent)
        }
    }
}
