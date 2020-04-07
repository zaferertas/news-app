package com.xxxxx.newsapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xxxxx.newsapplication.databinding.MainFragmentBinding
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.let { //to share the same view model between fragments
            viewModel = ViewModelProvider(it, mainViewModelFactory).get(MainViewModel::class.java)
        }

        val binding = MainFragmentBinding.inflate(inflater, container, false)
        val adapter = MainListAdapter(
            ItemClickListener { item ->
                viewModel.onItemSelected(item)
                showDetailsFragment()
            })

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.mainRecyclerView.adapter = adapter

        viewModel.newsList.observe(viewLifecycleOwner, Observer { items ->
            adapter.setItems(items)
        })

        return binding.root
    }

    private fun showDetailsFragment() {
        val detailsFragment = DetailsFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, detailsFragment)
            addToBackStack(null)
        }
        transaction.commit()
    }

}