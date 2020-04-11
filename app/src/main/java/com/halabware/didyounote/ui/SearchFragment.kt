package com.halabware.didyounote.ui

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
import com.halabware.didyounote.adapter.NoteAdapter
import com.halabware.didyounote.adapter.NoteClickListener
import com.halabware.didyounote.database.NoteDatabase
import com.halabware.didyounote.databinding.FragmentSearchBinding
import com.halabware.didyounote.viewmodelfactories.SearchViewModelFactory
import com.halabware.didyounote.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import com.halabware.didyounote.formatSearchQueryString

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

        binding.lifecycleOwner = this

        val adapter = NoteAdapter(NoteClickListener { noteId ->
            this.findNavController().navigate(
                SearchFragmentDirections
                    .actionSearchFragmentToNoteDetailsFragment(noteId)
            )
        })


        binding.searchView.apply {
            isIconified = false

            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.search(query).observe(viewLifecycleOwner, Observer {
                        adapter.submitList(it)
                    })
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return true
                }
            })
        }

        binding.searchResultsRecyclerView.adapter = adapter

        return binding.root
    }

}