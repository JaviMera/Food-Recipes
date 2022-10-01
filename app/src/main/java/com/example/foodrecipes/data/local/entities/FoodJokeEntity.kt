package com.example.foodrecipes.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.models.recipes.FoodJoke
import com.example.foodrecipes.util.Constants

@Entity(tableName = Constants.FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}