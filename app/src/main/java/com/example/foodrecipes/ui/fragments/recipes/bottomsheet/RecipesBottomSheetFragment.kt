package com.example.foodrecipes.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentRecipesBottomSheetBinding
import com.example.foodrecipes.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipes.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodrecipes.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_recipes_bottom_sheet.view.*
import java.util.*

class RecipesBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentRecipesBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipesBottomSheetBinding.inflate(layoutInflater, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(value.selectedDietTypeId, binding.dietTypeChipGroup)
        }

        binding.mealTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds[0])
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = checkedIds[0]
        }

        binding.dietTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds[0])
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            dietTypeChip = selectedMealType
            dietTypeChipId = checkedIds[0]
        }

        binding.applyButton.setOnClickListener {

            recipesViewModel.saveMealAndDietType(
                mealType = mealTypeChip,
                mealTypeId = mealTypeChipId,
                dietType = dietTypeChip,
                dietTypeId = dietTypeChipId
            )

            findNavController().navigate(
                RecipesBottomSheetFragmentDirections.actionRecipesBottomSheetFragmentToRecipesFragment(
                    true
                )
            )
        }
        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }
}