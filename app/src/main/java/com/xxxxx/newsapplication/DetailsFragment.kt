package com.xxxxx.newsapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xxxxx.newsapplication.databinding.DetailsFragmentBinding
import javax.inject.Inject

class DetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.let {
            viewModel = ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java)
        }

        val binding = DetailsFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.selectedNews.observe(viewLifecycleOwner, Observer { selectedNews ->
            binding.selectedItem = selectedNews
        })

        return binding.root
    }

}
