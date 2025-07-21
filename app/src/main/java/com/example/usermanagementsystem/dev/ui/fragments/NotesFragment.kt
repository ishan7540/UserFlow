package com.example.usermanagementsystem.dev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.usermanagementsystem.databinding.FragmentNotesBinding
import com.example.usermanagementsystem.dev.core.viewmodel.NoteViewModel
import com.example.usermanagementsystem.dev.ui.adapters.NoteAdapter

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)

        adapter = NoteAdapter(listOf()) { note ->
            val action = NotesFragmentDirections.actionNotesFragmentToEditFragment(note)
            findNavController().navigate(action)
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.adapter = adapter

        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            adapter.setNotes(notes)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToAddFragment())
        }

        return binding.root
    }
}
