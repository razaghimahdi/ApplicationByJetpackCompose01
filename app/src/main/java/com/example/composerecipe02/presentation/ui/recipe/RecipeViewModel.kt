package com.example.composerecipe02.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipe02.domain.model.Recipe
import com.example.composerecipe02.repository.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Named


const val STATE_KEY_RECIPE = "state.key.recipeId"

class RecipeViewModel
@ViewModelInject
constructor(
    private val recipeRepository: RecipeRepository,
    private @Named("auth_token") val token:String,
    @Assisted private val state:SavedStateHandle,
):ViewModel(){

    val TAG="RecipeViewModel"

    val recipe: MutableState<Recipe?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    init {
        //restore if process dies
        state.get<Int>(STATE_KEY_RECIPE)?.let { recipeId->
            onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }
    }

    fun onTriggerEvent(event:RecipeEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is RecipeEvent.GetRecipeEvent->{
                        if (recipe.value == null){
                            getRecipe(event.id)
                        }
                    }
                }

            }catch (e:Exception){
                Log.i(TAG, "onTriggerEvent: e ${e},${e.cause} ")
            }
        }
    }

    private suspend fun getRecipe(id:Int){
        loading.value=true

        //simulate a delay to show loading
        delay(1000)

        val recipe = recipeRepository.get(token,id)
        this.recipe.value=recipe

        state.set(STATE_KEY_RECIPE,recipe.id)

        loading.value=false


    }


}