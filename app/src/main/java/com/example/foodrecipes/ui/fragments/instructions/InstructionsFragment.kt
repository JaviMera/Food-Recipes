package com.example.foodrecipes.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentInstructionsBinding
import com.example.foodrecipes.util.Constants
import com.example.foodrecipes.viewmodels.MainViewModel

class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInstructionsBinding.inflate(layoutInflater, container, false)

        val resultId = arguments?.getInt(Constants.RECIPE_RESULT_KEY)!!
        binding.instructionsWebview.webViewClient = object : WebViewClient() {}

        mainViewModel.getRecipe(resultId)
        mainViewModel.recipeResponse.observe(viewLifecycleOwner){
            it.data?.let { recipe ->
                binding.instructionsWebview.loadUrl(recipe.sourceUrl)
            }
        }
        return binding.root
    }
}