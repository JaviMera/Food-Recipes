package com.example.foodrecipes.ui.fragments.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentOverviewBinding
import com.example.foodrecipes.util.Constants.Companion.RECIPE_RESULT_KEY
import com.example.foodrecipes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup
import org.jsoup.helper.DataUtil

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
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

        _binding = FragmentOverviewBinding.inflate(layoutInflater, container, false)
        val resultId = arguments?.getInt(RECIPE_RESULT_KEY)!!

        mainViewModel.getRecipe(resultId)
        mainViewModel.recipeResponse.observe(viewLifecycleOwner){

            it?.data?.let { recipe ->
                binding.mainImageview.load(recipe.image)
                binding.titleTextview.text = recipe.title
                binding.likesTextview.text = recipe.aggregateLikes.toString()
                binding.timeTextview.text = recipe.readyInMinutes.toString()
                binding.summaryTextview.text = Jsoup.parse(recipe.summary).text()

                if(recipe.vegetarian){
                    binding.vegetarianImageview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    binding.vegetarianTextview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }

                if(recipe.vegan){
                    binding.veganImageview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    binding.veganTextview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }

                if(recipe.glutenFree){
                    binding.glutenFreeImageview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    binding.glutenFreeTextview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }

                if(recipe.dairyFree){
                    binding.dairyImageview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    binding.dairyTextview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }

                if(recipe.veryHealthy){
                    binding.healthyImageview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    binding.healthyTextview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }

                if(recipe.cheap){
                    binding.cheapImageview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    binding.cheapTextview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }
            }
        }
        return binding.root
    }
}