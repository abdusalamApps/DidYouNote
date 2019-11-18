package com.halabware.didyounote.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
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
import com.halabware.didyounote.viewmodelfactories.NoteDetailsViewModelFactory
import com.halabware.didyounote.viewmodels.NoteDetailsViewModel

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

        viewModel.showDeleteDialog.observe(this, Observer {
            if (it) {
//                Show delete dialog
                val builder: AlertDialog.Builder? = activity?.let {
                    AlertDialog.Builder(it)
                }
                builder?.setMessage("Delete this note?")
                builder?.setPositiveButton("Delete", DialogInterface.OnClickListener{dialog, id ->
                    viewModel.onDelete()
                })
                builder?.setNegativeButton("Cancel", null)
                builder?.show()
                viewModel.doneShowingDeleteDialog()
            }
        })

        return binding.root
    }
}