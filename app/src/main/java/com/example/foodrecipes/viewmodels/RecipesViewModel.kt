package com.example.foodrecipes.viewmodels

import androidx.lifecycle.ViewModel
import com.example.foodrecipes.util.Constants.Companion.API_KEY
import com.example.foodrecipes.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foodrecipes.util.Constants.Companion.QUERY_API_KEY
import com.example.foodrecipes.util.Constants.Companion.QUERY_DIET
import com.example.foodrecipes.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foodrecipes.util.Constants.Companion.QUERY_NUMBER
import com.example.foodrecipes.util.Constants.Companion.QUERY_TYPE

class RecipesViewModel : ViewModel() {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}