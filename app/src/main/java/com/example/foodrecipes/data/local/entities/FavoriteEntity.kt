package com.example.foodrecipes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.models.recipes.Result
import com.example.foodrecipes.util.Constants.Companion.FAVORITE_TABLE

@Entity(tableName = FAVORITE_TABLE)
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)

