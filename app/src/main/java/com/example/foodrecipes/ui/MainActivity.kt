package com.example.foodrecipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodrecipes.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var _navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _navController = (supportFragmentManager
            .findFragmentById(R.id.navigationHostFragment) as NavHostFragment
                ).navController

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.recipesFragment,
            R.id.favoriteRecipesFragment,
            R.id.foodJokeFragment
        ))

        bottomNavigationView.setupWithNavController(_navController)
        setupActionBarWithNavController(_navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return _navController.navigateUp() ||  super.onSupportNavigateUp()
    }
}