package com.example.foodrecipes.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodrecipes.data.local.RecipeEntity
import com.example.foodrecipes.models.recipes.FoodRecipe
import com.example.foodrecipes.util.NetworkResult
import org.jsoup.Jsoup

@BindingAdapter("apiResponse", "readDatabase", requireAll = true)
fun setErrorImageVisibility(
    imageView: ImageView,
    apiResponse: NetworkResult<FoodRecipe>?,
    database: List<RecipeEntity>?
) {
    if (database != null) {
        if (apiResponse is NetworkResult.Error && database.isEmpty()) {
            imageView.visibility = View.VISIBLE
        } else if (apiResponse is NetworkResult.Loading || apiResponse is NetworkResult.Success) {
            imageView.visibility = View.INVISIBLE
        }
    }
}

@BindingAdapter("apiResponse2", "readDatabase2", requireAll = true)
fun setErrorTextVisibility(
    textView: TextView,
    apiResponse: NetworkResult<FoodRecipe>?,
    database: List<RecipeEntity>?
){
    if (database != null) {
        if (apiResponse is NetworkResult.Error && database.isEmpty()) {
            textView.visibility = View.VISIBLE
            textView.text = apiResponse.message.toString()
        } else if (apiResponse is NetworkResult.Loading || apiResponse is NetworkResult.Success) {
            textView.visibility = View.INVISIBLE
        }
    }
}