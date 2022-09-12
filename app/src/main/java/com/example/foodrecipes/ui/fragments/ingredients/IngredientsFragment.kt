package com.example.foodrecipes.ui.fragments.ingredients

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.foodrecipes.R
import com.example.foodrecipes.adapters.IngredientsAdapter
import com.example.foodrecipes.databinding.FragmentIngredientsBinding
import com.example.foodrecipes.databinding.FragmentRecipesBinding
import com.example.foodrecipes.util.Constants
import com.example.foodrecipes.viewmodels.MainViewModel

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private val adapter: IngredientsAdapter by lazy {IngredientsAdapter()}
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        _binding = FragmentIngredientsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.ingredientsRecyclerview.adapter = adapter

        val resultId = arguments?.getInt(Constants.RECIPE_RESULT_KEY)!!

        mainViewModel.getRecipe(resultId)
        mainViewModel.recipeResponse.observe(viewLifecycleOwner){
            it.data?.let { recipe ->
                adapter.submitList(recipe.extendedIngredients)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}