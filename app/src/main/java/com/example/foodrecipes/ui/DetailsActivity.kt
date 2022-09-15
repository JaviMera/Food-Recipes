package com.example.foodrecipes.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.navArgs
import androidx.viewpager.widget.PagerAdapter
import com.example.foodrecipes.R
import com.example.foodrecipes.data.local.entities.FavoriteEntity
import com.example.foodrecipes.databinding.ActivityDetailsBinding
import com.example.foodrecipes.ui.fragments.ingredients.IngredientsFragment
import com.example.foodrecipes.ui.fragments.instructions.InstructionsFragment
import com.example.foodrecipes.ui.fragments.overview.OverviewFragment
import com.example.foodrecipes.util.Constants.Companion.RECIPE_RESULT_KEY
import com.example.foodrecipes.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val args: DetailsActivityArgs by navArgs()

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>().apply {
            add(OverviewFragment())
            add(IngredientsFragment())
            add(InstructionsFragment())
        }

        val titles = ArrayList<String>().apply {
            add("Overview")
            add("Ingredients")
            add("Instructions")
        }

        val bundle = Bundle().apply {
            putInt(RECIPE_RESULT_KEY, args.resultId)
        }

        binding.viewPager.apply {
            adapter = com.example.foodrecipes.adapters.PagerAdapter(
                bundle,
                fragments,
                this@DetailsActivity
            )
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.details_menu, menu)
                val menuItem = menu.findItem(R.id.save_to_favorites_menu)
                mainViewModel.readFavoriteRecipes.observe(this@DetailsActivity) { favoriteEntites ->
                    try {
                        val match =
                            favoriteEntites.firstOrNull { favorite -> favorite.result.id == args.resultId }
                        if (match != null) {
                            changeMenuItemColor(menuItem, R.color.yellow)
                        } else {
                            changeMenuItemColor(menuItem, R.color.white)
                        }
                    } catch (e: Exception) {
                        Log.d("DetailsActivity", e.message.toString())
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.save_to_favorites_menu -> {

                        mainViewModel.getRecipe(args.resultId)
                        mainViewModel.recipeResponse.observe(this@DetailsActivity) { recipe ->
                            recipe.data?.let { result ->
                                val favoriteRecipe =
                                    mainViewModel.readFavoriteRecipes.value?.firstOrNull {
                                        it.result.id == args.resultId
                                    }

                                if (favoriteRecipe == null) {
                                    val entity = FavoriteEntity(0, result)
                                    mainViewModel.insertFavoriteRecipe(entity)
                                    changeMenuItemColor(menuItem, R.color.yellow)
                                    showSnackBar("Recipe Saved!")
                                } else {
                                    val favoriteEntity =
                                        FavoriteEntity(favoriteRecipe.id, result)
                                    mainViewModel.deleteFavoriteRecipe(favoriteEntity)
                                    changeMenuItemColor(menuItem, R.color.white)
                                    showSnackBar("Removed Recipe from favorites!")
                                }
                            }
                        }
                        true
                    }
                    else -> true
                }
            }

        }, this@DetailsActivity, Lifecycle.State.RESUMED)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this@DetailsActivity, color))
    }

    private fun showSnackBar(message: String) {
        Snackbar
            .make(binding.detailsLayout, message, Snackbar.LENGTH_SHORT)
            .setAction("Okay") {}
            .show()
    }
}