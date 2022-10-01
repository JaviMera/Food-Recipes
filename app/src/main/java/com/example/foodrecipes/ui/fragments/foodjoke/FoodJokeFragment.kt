package com.example.foodrecipes.ui.fragments.foodjoke

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentFoodJokeBinding
import com.example.foodrecipes.util.Constants.Companion.API_KEY
import com.example.foodrecipes.util.NetworkResult
import com.example.foodrecipes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes_bottom_sheet.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {
    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()
    private var foodJoke = "No Food Joke"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodJokeBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.foodJokeTextview.text = response.data?.text

                    if (response.data != null) {
                        foodJoke = response.data.text
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                    loadDataFromCache()
                }
                is NetworkResult.Loading -> {
                    Log.d("FoodJokeFragment", "Loading")
                }
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.food_joke_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.share_food_joke_menu -> {

                        val shareIntent = Intent().apply {
                            this.action = Intent.ACTION_SEND
                            this.putExtra(Intent.EXTRA_TEXT, foodJoke)
                            this.type = "text/plain"
                        }

                        startActivity(shareIntent)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner) { database ->
                if (!database.isNullOrEmpty()) {
                    binding.foodJokeTextview.text = database[0].foodJoke.text
                    foodJoke = database[0].foodJoke.text
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}