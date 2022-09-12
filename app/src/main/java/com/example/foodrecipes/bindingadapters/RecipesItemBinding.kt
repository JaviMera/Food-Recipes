package com.example.foodrecipes.bindingadapters

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.foodrecipes.R
import com.example.foodrecipes.models.recipes.Result
import com.example.foodrecipes.ui.fragments.recipes.RecipesFragmentDirections
import com.example.foodrecipes.util.Constants.Companion.BASE_IMAGE_URL
import org.jsoup.Jsoup

@BindingAdapter("imageFromUrl")
fun setImageFromUrl(imageView: ImageView, imageUrl: String){
    imageView.load(imageUrl){
        crossfade(600)
        error(R.drawable.ic_error_placeholder)
    }
}

@BindingAdapter("ingredientImageUrl")
fun setIngredientImageUrl(imageView: ImageView, imageUrl: String){

    imageView.load("${BASE_IMAGE_URL}${imageUrl}"){
        crossfade(600)
        error(R.drawable.ic_error_placeholder)
    }
}

@BindingAdapter("veganImageColor")
fun setVeganImageColor(imageView: ImageView, isVegan: Boolean){
    if(isVegan){
        imageView.setColorFilter(ContextCompat.getColor(imageView.context, R.color.green));
    }
}

@BindingAdapter("veganTextColor")
fun setVeganTextColor(textView: TextView, isVegan: Boolean){
    if(isVegan){
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.green))
    }
}

@BindingAdapter("onRecipeClickListener")
fun onRecipeClickListener(recipeItemLayout: ConstraintLayout, result: Result){
    recipeItemLayout.setOnClickListener {
        try{
            val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result.id)
            recipeItemLayout.findNavController().navigate(action)
        }catch (exception: Exception){
            Log.d("onRecipeClickListener", exception.toString())
        }
    }
}

@BindingAdapter("parseHtml")
fun parseHtml(textView: TextView, description: String?){
    description?.let {
        textView.text = Jsoup.parse(description).text()
    }
}