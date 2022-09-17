package com.example.foodrecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FavoriteRecipesItemLayoutBinding
import com.example.foodrecipes.models.recipes.FavoriteRecipe

class FavoriteRecipesAdapter : ListAdapter<FavoriteRecipe, FavoriteRecipesAdapter.ViewHolder>(FavoriteDiffCallBack()) {

    class ViewHolder(private val binding: FavoriteRecipesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: FavoriteRecipe) {
            binding.favoriteRecipe = result
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate(
            inflater,
            R.layout.favorite_recipes_item_layout,
            parent,
            false
        ) as FavoriteRecipesItemLayoutBinding
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
    }

    class FavoriteDiffCallBack : DiffUtil.ItemCallback<FavoriteRecipe>() {
        override fun areItemsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
            return oldItem == newItem
        }
    }
}