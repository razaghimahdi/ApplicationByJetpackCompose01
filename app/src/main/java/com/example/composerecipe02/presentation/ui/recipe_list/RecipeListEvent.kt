package com.example.composerecipe02.presentation.ui.recipe_list

import com.example.composerecipe02.domain.model.Recipe

sealed class RecipeListEvent {

    object NewSearchEvent:RecipeListEvent()

    object NextPageEvent:RecipeListEvent()

    // restore after process death
    object RestoreStateEvent:RecipeListEvent()


}