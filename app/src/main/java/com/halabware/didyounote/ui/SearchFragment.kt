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
import com.halabware.didyounote.adapter.NoteAdapter
import com.halabware.didyounote.adapter.NoteClickListener
import com.halabware.didyounote.database.NoteDatabase
import com.halabware.didyounote.databinding.FragmentSearchBinding
import com.halabware.didyounote.viewmodelfactories.SearchViewModelFactory
import com.halabware.didyounote.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import androidx.appcompat.widget.SearchView.OnQueryTextListener;

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = SearchViewModelFactory(dataSource)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        val adapter = NoteAdapter(NoteClickListener { noteId ->
            this.findNavController().navigate(
                NotesFragmentDirections
                    .ActionNotesFragmentToNoteDetailsFragment(noteId)
            )
        })

        viewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.searchView.setOnQueryTextListener(object :
            OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.search(query)
                adapter.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.search(newText)
                adapter.notifyDataSetChanged()
                return true
            }
        })


        binding.searchResultsRecyclerView.adapter = adapter

        return binding.root
    }

}