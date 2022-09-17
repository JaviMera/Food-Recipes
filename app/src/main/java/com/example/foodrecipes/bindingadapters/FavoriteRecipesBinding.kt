package com.example.foodrecipes.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.adapters.FavoriteRecipesAdapter
import com.example.foodrecipes.data.local.entities.FavoriteEntity
import com.example.foodrecipes.models.recipes.FavoriteRecipe

@BindingAdapter("viewVisibility", "setData", requireAll = false)
fun setDataAndViewVisibility(
    view: View,
    favoriteRecipe: List<FavoriteEntity>?,
    adapter: FavoriteRecipesAdapter?
) {
    if (favoriteRecipe.isNullOrEmpty()) {
        when (view) {
            is ImageView -> {
                view.visibility = View.VISIBLE
            }
            is TextView -> {
                view.visibility = View.VISIBLE
            }
            is RecyclerView -> {
                view.visibility = View.INVISIBLE
            }
        }
    } else {
        when (view) {
            is ImageView -> {
                view.visibility = View.INVISIBLE
            }
            is TextView -> {
                view.visibility = View.INVISIBLE
            }
            is RecyclerView -> {
                view.visibility = View.VISIBLE
                adapter?.submitList(favoriteRecipe.map { favoriteEntity ->
                    FavoriteRecipe(
                        favoriteEntity.id,
                        favoriteEntity.result
                    )
                })
            }
        }
    }
}