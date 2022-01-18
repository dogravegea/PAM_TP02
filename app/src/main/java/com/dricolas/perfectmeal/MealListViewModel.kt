package com.dricolas.perfectmeal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dricolas.perfectmeal.rest.MealAPI
import com.dricolas.perfectmeal.rest.MealListItem
import kotlinx.coroutines.launch


class MealListViewModel : ViewModel() {

    private var api = MealAPI()

    private val mealsList = MutableLiveData<ArrayList<MealListItem>>().apply {
        value = ArrayList<MealListItem>()
        loadMeals(value)
    }

    fun getMeals(): LiveData<ArrayList<MealListItem>> {
        return mealsList
    }

    private fun loadMeals(value : ArrayList<MealListItem>? ) {
        // Do an asynchronous operation to fetch meals.
        for(i in 1..1) {
            viewModelScope.launch() {
                var lst = value

                for(j in 1..10) {
                    try {
                        lst?.add(api.getRandomRecipe())
                    } catch (e: Throwable) {
                        Log.e("PARSE_ERROR", "An error occurred while parsing the json response.")
                    }
                }
                mealsList.postValue(lst)
            }
        }
    }
}
