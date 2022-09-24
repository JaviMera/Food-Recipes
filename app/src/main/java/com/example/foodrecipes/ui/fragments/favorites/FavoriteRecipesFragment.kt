package com.example.foodrecipes.ui.fragments.favorites

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.adapters.FavoriteRecipesAdapter
import com.example.foodrecipes.data.local.entities.FavoriteEntity
import com.example.foodrecipes.databinding.FragmentFavoriteRecipesBinding
import com.example.foodrecipes.models.recipes.FavoriteRecipe
import com.example.foodrecipes.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.favorite_recipes_item_layout.view.*

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment(), ActionMode.Callback {

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoriteRecipe>()
    private var favoriteSelected = emptyList<FavoriteRecipe>()
    private var viewHolders = arrayListOf<FavoriteRecipesAdapter.ViewHolder>()

    private lateinit var _actionMode: ActionMode

    private val _adapter: FavoriteRecipesAdapter by lazy {
        FavoriteRecipesAdapter(
            FavoriteRecipesAdapter.OnClickListener { favoriteRecipe, viewHolder ->
                if (multiSelection) {
                    viewHolders.add(viewHolder)
                    if (selectedRecipes.contains(favoriteRecipe)) {
                        selectedRecipes.remove(favoriteRecipe)
                        changeRecipeStyle(
                            viewHolder,
                            R.color.cardBackgroundColor,
                            R.color.mediumGray
                        )
                    } else {
                        selectedRecipes.add(favoriteRecipe)
                        changeRecipeStyle(
                            viewHolder,
                            R.color.cardBackgroundLightColor,
                            R.color.colorPrimary
                        )
                    }
                    applyActionModeTitle()
                } else {
                    findNavController().navigate(
                        FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                            favoriteRecipe.result.id
                        )
                    )
                }
            },
            FavoriteRecipesAdapter.OnLongClickListener { favoriteRecipe, viewHolder ->
                multiSelection = true
                requireActivity().startActionMode(this)
                viewHolders.add(viewHolder)
                if (selectedRecipes.contains(favoriteRecipe)) {
                    selectedRecipes.remove(favoriteRecipe)
                    changeRecipeStyle(
                        viewHolder,
                        R.color.cardBackgroundColor,
                        R.color.mediumGray
                    )
                } else {
                    selectedRecipes.add(favoriteRecipe)
                    changeRecipeStyle(
                        viewHolder,
                        R.color.cardBackgroundLightColor,
                        R.color.colorPrimary
                    )
                }
                applyActionModeTitle()
            },
        )
    }

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoriteRecipesBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.adapter = _adapter

        binding.favoriteRecipeRecyclerview.adapter = _adapter
        binding.favoriteRecipeRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.favorite_recipes_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if(menuItem.itemId == R.id.delete_all_favorite_recipes_menu){
                    mainViewModel.deleteAllFavoriteRecipes()
                    Snackbar
                        .make(binding.favoriteRecipeRecyclerview,
                        "All recipes removed!",
                        Snackbar.LENGTH_SHORT)
                        .setAction("Okay"){}
                        .show()
                }
                return true
            }

        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.contextualStatusBarColor)

        _actionMode = actionMode!!
        return true
    }

    override fun onPrepareActionMode(
        actionMode: ActionMode?,
        menu: Menu?
    ): Boolean {
        return true
    }

    override fun onActionItemClicked(
        actionMode: ActionMode?,
        menu: MenuItem?
    ): Boolean {
        if (menu?.itemId == R.id.delete_favorite_recipe_menu) {
            selectedRecipes
                .map { recipe -> FavoriteEntity(recipe.id, recipe.result) }
                .forEach {
                    mainViewModel.deleteFavoriteRecipe(it)
                }

            multiSelection = false
            selectedRecipes.clear()
            actionMode?.finish()
            Snackbar
                .make(binding.favoriteRecipeRecyclerview,
                    "Recipes removed!",
                    Snackbar.LENGTH_SHORT)
                .setAction("Okay"){}
                .show()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        multiSelection = false
        selectedRecipes.clear()
        viewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.statusBarColor)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> _actionMode.finish()
            1 -> {
                _actionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                _actionMode.title = "${selectedRecipes.size} item(s) selected"
            }
        }
    }

    private fun changeRecipeStyle(
        holder: FavoriteRecipesAdapter.ViewHolder,
        backgroundColor: Int,
        stroke: Int
    ) {
        holder.itemView.favorite_recipes_item_layout.setBackgroundColor(
            ContextCompat.getColor(requireActivity(), backgroundColor)
        )

        holder.itemView.favorite_item_cardview.strokeColor =
            ContextCompat.getColor(requireActivity(), stroke)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        if (this::_actionMode.isInitialized) {
            _actionMode.finish()
        }
    }
}