package com.example.composerecipe02.presentation.ui.recipe

sealed class RecipeEvent {

    data class GetRecipeEvent(
        val id:Int
    ):RecipeEvent()


}