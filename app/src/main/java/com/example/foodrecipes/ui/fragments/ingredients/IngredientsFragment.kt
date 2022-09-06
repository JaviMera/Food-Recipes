package com.example.foodrecipes.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentIngredientsBinding
import com.example.foodrecipes.databinding.FragmentRecipesBinding
import com.example.foodrecipes.util.Constants

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentIngredientsBinding.inflate(layoutInflater, container, false)

        val resultId = arguments?.getInt(Constants.RECIPE_RESULT_KEY)

        // Inflate the layout for this fragment
        return binding.root
    }
}