package com.example.foodrecipes.bindingadapters

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodrecipes.data.local.entities.FoodJokeEntity
import com.example.foodrecipes.models.recipes.FoodJoke
import com.example.foodrecipes.util.NetworkResult
import com.google.android.material.card.MaterialCardView

@BindingAdapter("response", "database")
fun setCardAndProgressVisibility(
    view: View,
    response: NetworkResult<FoodJoke>?,
    database: List<FoodJokeEntity>?
) {
    when (response) {
        is NetworkResult.Error -> {
            when (view) {
                is ProgressBar -> {
                    view.visibility = View.INVISIBLE
                }
                is MaterialCardView -> {
                    view.visibility = View.VISIBLE
                    if (database != null) {
                        if (database.isEmpty()) {
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
        is NetworkResult.Loading -> {
            when (view) {
                is ProgressBar -> {
                    view.visibility = View.VISIBLE
                }
                is MaterialCardView -> {
                    view.visibility = View.VISIBLE
                }
            }
        }
        is NetworkResult.Success -> {
            when (view) {
                is ProgressBar -> {
                    view.visibility = View.INVISIBLE
                }
                is MaterialCardView -> {
                    view.visibility = View.VISIBLE
                }
            }
        }
        else -> {

        }
    }
}

@BindingAdapter("foodJokeErrorResponse", "foodJokeErrorDatabase", requireAll = true)
fun setErrorViewsVisibility(
    view: View,
    apiResponse: NetworkResult<FoodJoke>?,
    database: List<FoodJokeEntity>?
) {
    database?.let { database ->

        if (database.isEmpty()) {
            view.visibility = View.VISIBLE

            if (view is TextView) {
                if (apiResponse != null) {
                    view.text = apiResponse.message.toString()
                }
            }
        }
        if (apiResponse is NetworkResult.Success) {
            view.visibility = View.INVISIBLE
        }
    }

}