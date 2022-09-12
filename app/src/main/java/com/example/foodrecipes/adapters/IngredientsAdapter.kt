package com.example.foodrecipes.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.IngredientsItemBinding
import com.example.foodrecipes.models.recipes.ExtendedIngredient

class IngredientsAdapter : ListAdapter<ExtendedIngredient, IngredientsAdapter.ViewHolder>(IngredientDiffCallback()) {

    class ViewHolder(private val binding: IngredientsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: ExtendedIngredient) {
            binding.ingredient = ingredient
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate(
            inflater,
            R.layout.ingredients_item,
            parent,
            false
        ) as IngredientsItemBinding
        Log.d("IngredientsAdapter", "Creating ViewHolder")
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = getItem(position)
        Log.d("IngredientsAdapter", result.toString())
        holder.bind(result)
    }

    class IngredientDiffCallback : DiffUtil.ItemCallback<ExtendedIngredient>() {
        override fun areItemsTheSame(oldItem: ExtendedIngredient, newItem: ExtendedIngredient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ExtendedIngredient, newItem: ExtendedIngredient): Boolean {
            return oldItem == newItem
        }
    }
}