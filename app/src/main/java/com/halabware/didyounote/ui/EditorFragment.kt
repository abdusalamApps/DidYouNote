package com.halabware.didyounote.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.halabware.didyounote.R
import com.halabware.didyounote.database.NoteDatabase
import com.halabware.didyounote.databinding.FragmentEditorBinding
import com.halabware.didyounote.databinding.FragmentNotesBinding
import com.halabware.didyounote.viewmodelfactories.EditorViewModelFactory
import com.halabware.didyounote.viewmodelfactories.NotesViewModelFactory
import com.halabware.didyounote.viewmodels.EditorViewModel
import com.halabware.didyounote.viewmodels.NotesViewModel

/**
 * A simple [Fragment] subclass.
 */
class EditorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEditorBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_editor,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = EditorViewModelFactory(dataSource)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(EditorViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToNotes.observe(this, Observer {
            if (it) {
                this.findNavController().popBackStack()
                viewModel.doneNavigatingToNotes()
            }
        })

        showKeyboard()


        return binding.root

    }

    fun showKeyboard() {
        val inputMethodManager =
            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}
