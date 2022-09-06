package com.example.foodrecipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import androidx.viewpager.widget.PagerAdapter
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.ActivityDetailsBinding
import com.example.foodrecipes.ui.fragments.ingredients.IngredientsFragment
import com.example.foodrecipes.ui.fragments.instructions.InstructionsFragment
import com.example.foodrecipes.ui.fragments.overview.OverviewFragment
import com.example.foodrecipes.util.Constants.Companion.RECIPE_RESULT_KEY
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val args: DetailsActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>().apply {
            add(OverviewFragment())
            add(IngredientsFragment())
            add(InstructionsFragment())
        }

        val titles = ArrayList<String>().apply {
            add("Overview")
            add("Ingredients")
            add("Instructions")
        }

        val bundle = Bundle().apply {
            putInt(RECIPE_RESULT_KEY, args.resultId)
        }

        binding.viewPager.apply {
            adapter = com.example.foodrecipes.adapters.PagerAdapter(
                bundle,
                fragments,
                this@DetailsActivity
            )
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}