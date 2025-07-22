package com.example.usermanagementsystem.dev.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.usermanagementsystem.R
import com.example.usermanagementsystem.databinding.FragmentAddBinding
import com.example.usermanagementsystem.dev.core.models.Note
import com.example.usermanagementsystem.dev.core.viewmodel.NoteViewModel

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: NoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.save.setOnClickListener {
            val title = binding.addNoteTitle.text.toString().trim()
            val desc = binding.addNoteDesc.text.toString().trim()

            val email = viewModel.getSessionEmail()
            val userName = viewModel.getSessionName()

            if (title.isEmpty() || desc.isEmpty()) {
                Toast.makeText(requireContext(), "Title and Description can't be empty", Toast.LENGTH_SHORT).show()
            } else if (email == null || userName == null) {
                Toast.makeText(requireContext(), "Session not found. Please login again.", Toast.LENGTH_SHORT).show()
            } else {
                val note = Note(
                    noteTitle = title,
                    noteDesc = desc,
                    email = email,
                    userName = userName,
                    timestamp = System.currentTimeMillis()
                )

                viewModel.insert(note)
                Toast.makeText(requireContext(), "Note Added", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_notesFragment)
            }
            Log.d("AddFragment", "email: $email, userName: $userName")


        }

        return binding.root
    }
}

