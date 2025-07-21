package com.example.usermanagementsystem.dev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.usermanagementsystem.R
import com.example.usermanagementsystem.databinding.FragmentAddBinding
import com.example.usermanagementsystem.dev.core.models.Note
import com.example.usermanagementsystem.dev.core.viewmodel.NoteViewModel

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.save.setOnClickListener {
            val title = binding.addNoteTitle.text.toString().trim()
            val desc = binding.addNoteDesc.text.toString().trim()

            if (title.isNotEmpty() && desc.isNotEmpty()) {
                val note = Note(
                    noteTitle = title, noteDesc = desc,
                    id = 0 ,timestamp = System.currentTimeMillis()
                )
                viewModel.insert(note)
                Toast.makeText(requireContext(), "Note Added", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_notesFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}
