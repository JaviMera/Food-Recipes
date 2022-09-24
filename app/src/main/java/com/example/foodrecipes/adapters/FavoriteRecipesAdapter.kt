package com.example.foodrecipes.adapters

import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FavoriteRecipesItemLayoutBinding
import com.example.foodrecipes.models.recipes.FavoriteRecipe
import kotlinx.android.synthetic.main.favorite_recipes_item_layout.view.*

class FavoriteRecipesAdapter(
    private val onClickListener: OnClickListener,
    private val onLongClickListener: OnLongClickListener,
) : ListAdapter<FavoriteRecipe, FavoriteRecipesAdapter.ViewHolder>(FavoriteDiffCallBack()) {

    class OnClickListener(val clickListener: (favoriteRecipe: FavoriteRecipe, holder: ViewHolder) -> Unit) {
        fun onClick(favoriteRecipe: FavoriteRecipe, holder: ViewHolder) =
            clickListener(favoriteRecipe, holder)
    }

    class OnLongClickListener(val longClickListener: (favoriteRecipe: FavoriteRecipe, holder: ViewHolder) -> Unit) {
        fun onLongClick(favorite: FavoriteRecipe, holder: ViewHolder): Boolean {
            longClickListener(favorite, holder)
            return true
        }
    }

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
        val favoriteRecipeItem = getItem(position)
        holder.bind(favoriteRecipeItem)
        holder.itemView.favorite_recipes_item_layout.setOnClickListener {
            onClickListener.onClick(favoriteRecipeItem, holder)
        }

        holder.itemView.favorite_recipes_item_layout.setOnLongClickListener {
            onLongClickListener.onLongClick(favoriteRecipeItem, holder)
        }
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