package com.halabware.didyounote.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.halabware.didyounote.R
import com.halabware.didyounote.adapter.NoteAdapter
import com.halabware.didyounote.adapter.NoteClickListener
import com.halabware.didyounote.database.NoteDatabase
import com.halabware.didyounote.databinding.FragmentNotesBinding
import com.halabware.didyounote.viewmodelfactories.NotesViewModelFactory
import com.halabware.didyounote.viewmodels.NotesViewModel

/**
 * A simple [Fragment] subclass.
 */
class NotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNotesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notes,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = NotesViewModelFactory(dataSource)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NotesViewModel::class.java)

        viewModel.navigateToEditor.observe(this, Observer {
            if (it) {
                this.findNavController()
                    .navigate(
                        NotesFragmentDirections
                            .actionNotesFragmentToEditorFragment())
                viewModel.doneNavigatingToEditor()
            }
        })

        val adapter = NoteAdapter(NoteClickListener { id ->
            Toast.makeText(context, "$id", Toast.LENGTH_LONG).show()
        })

        viewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        binding.notesRecyclerView.adapter = adapter

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        return binding.root
    }


}
