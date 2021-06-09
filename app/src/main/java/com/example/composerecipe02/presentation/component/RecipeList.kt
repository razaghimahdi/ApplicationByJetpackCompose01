package com.example.composerecipe02.presentation.component

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.composerecipe02.R
import com.example.composerecipe02.domain.model.Recipe
import com.example.composerecipe02.presentation.ui.recipe_list.PAGE_SIZE
import com.example.composerecipe02.presentation.ui.recipe_list.RecipeListEvent

@Composable
fun RecipeList(
    loading:Boolean,
    recipes:List<Recipe>,
    onChangeRecipeScrollPosition:(Int) ->Unit,
    page:Int,
    onNextPage:(RecipeListEvent.NextPageEvent)->Unit,
    navController: NavController,

    ) {


    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize()
    ) {

        LazyColumn {
            itemsIndexed(items = recipes) { index, recipe ->
                onChangeRecipeScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                    onNextPage(RecipeListEvent.NextPageEvent)
                }
                RecipeCard(recipe = recipe, onClick = {
                    val bundle = Bundle()
                    bundle.putInt("recipeId",recipe.id)
                     navController.navigate(R.id.viewRecipe,bundle)


                })
            }
        }

        CircularIndeterminateProgressBar(isDisplayed = loading)
        //DefaultSnackbar(snackbarHostState = scaffoldState.snackbarHostState
        //    ,onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() },
        //modifier = Modifier.align(Alignment.BottomCenter))
    }


}