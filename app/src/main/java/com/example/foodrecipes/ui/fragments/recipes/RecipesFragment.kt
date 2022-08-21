package com.example.foodrecipes.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.MainViewModel
import com.example.foodrecipes.R
import com.example.foodrecipes.adapters.RecipesAdapter
import com.example.foodrecipes.databinding.FragmentRecipesBinding
import com.example.foodrecipes.util.Constants.Companion.API_KEY
import com.example.foodrecipes.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_recipes.view.*

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

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
        mainViewModel.getRecipes(applyQueries())
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

    private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }
}