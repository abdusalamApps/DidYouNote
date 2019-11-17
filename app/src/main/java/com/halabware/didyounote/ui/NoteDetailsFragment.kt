package com.halabware.didyounote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.halabware.didyounote.R
import com.halabware.didyounote.database.NoteDatabase
import com.halabware.didyounote.databinding.FragmentNoteDetailsBinding
import com.halabware.didyounote.databinding.FragmentNotesBinding
import com.halabware.didyounote.viewmodelfactories.NoteDetailsViewModelFactory
import com.halabware.didyounote.viewmodelfactories.NotesViewModelFactory
import com.halabware.didyounote.viewmodels.NoteDetailsViewModel
import com.halabware.didyounote.viewmodels.NotesViewModel

class NoteDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNoteDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_note_details,
            container,
            false
        )


        val application = requireNotNull(this.activity).application

        val arguments = NoteDetailsFragmentArgs.fromBundle(arguments)

        val dataSource = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = NoteDetailsViewModelFactory(arguments.noteId, dataSource)
        val viewModel =
            ViewModelProviders
                .of(this, viewModelFactory).get(NoteDetailsViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.note.observe(this, Observer {
            if (it != null) {
                binding.note = it
            }
        })

        viewModel.navigateToNotes.observe(this, Observer {
            if (it) {
                this.findNavController().popBackStack()
                viewModel.doneNavigatingToNotes()
            }
        })

        return binding.root
    }
}