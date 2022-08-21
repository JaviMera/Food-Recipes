package com.example.foodrecipes.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.viewmodels.MainViewModel
import com.example.foodrecipes.adapters.RecipesAdapter
import com.example.foodrecipes.databinding.FragmentRecipesBinding
import com.example.foodrecipes.util.NetworkResult
import com.example.foodrecipes.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)

        setupRecyclerView()
        requestApiData()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = RecipesAdapter()
        binding.recipesRecyclerView.adapter = adapter
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipesRecyclerView.visibility = View.GONE
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.shimmerLayout.hideShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    binding.recipesRecyclerView.visibility = View.VISIBLE
                    response.data?.let { adapter.submitList(it.results) }
                }
                is NetworkResult.Error -> {
                    binding.shimmerLayout.hideShimmer()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    binding.shimmerLayout.startShimmer()
                }
            }
        }
    }
}