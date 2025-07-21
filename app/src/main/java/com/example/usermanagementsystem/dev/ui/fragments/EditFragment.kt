package com.example.usermanagementsystem.dev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.usermanagementsystem.databinding.FragmentEditBinding
import com.example.usermanagementsystem.dev.core.models.Note
import com.example.usermanagementsystem.dev.core.viewmodel.NoteViewModel


class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val args by navArgs<EditFragmentArgs>()
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var currentNote: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)

        currentNote = args.note

        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)


        binding.save.setOnClickListener {
            val updatedTitle = binding.editNoteTitle.text.toString().trim()
            val updatedDesc = binding.editNoteDesc.text.toString().trim()

            if (updatedTitle.isNotEmpty() && updatedDesc.isNotEmpty()) {
                val updatedNote = Note(
                    id = currentNote.id,
                    noteTitle = updatedTitle,
                    noteDesc = updatedDesc,
                            timestamp = currentNote.timestamp
                )
                noteViewModel.update(updatedNote)
                Toast.makeText(requireContext(), "Note Updated", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Fields can't be empty", Toast.LENGTH_SHORT).show()
            }
        }


        binding.delete.setOnClickListener {
            noteViewModel.delete(currentNote)
            Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        binding.delete.setOnClickListener {
            noteViewModel.delete(currentNote)
            Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }


        return binding.root
    }
}
