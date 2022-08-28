package com.example.foodrecipes.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodrecipes.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipes.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodrecipes.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.foodrecipes.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.foodrecipes.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.foodrecipes.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.zip.InflaterOutputStream
import javax.inject.Inject

val Context.dataSore: DataStore<Preferences> by preferencesDataStore(name= PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferenceKeys{
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(DEFAULT_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }

    private val dataStore: DataStore<Preferences> = context.dataSore

    suspend fun saveMealAndType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int){
        dataStore.edit {  preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch {  exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preferences ->
            MealAndDietType(
                selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE,
                selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0,
                selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE,
                selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            )
        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)