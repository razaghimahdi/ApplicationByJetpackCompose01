package com.example.composerecipe02.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private val viewModel:RecipeViewModel by viewModels()

    private var recipeId:MutableState<Int> = mutableStateOf(-1)
    
    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_recipe_list, container,
            false
        )
        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            Column(
                modifier = Modifier
                    .border(border = BorderStroke(1.dp, Color.Black))
                    .padding(16.dp)
            ) {
                Text(text = "This is RecipeFragment")
            }
        }

        return view
    }
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.getInt("recipeId")?.let { recipeId->
            this.recipeId.value=recipeId
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply { 
            setContent {

                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value


                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = if(recipe!!.id != -1) {"Secleted recipe id: ${recipe.title}"} else{"Loading..."},fontSize=21.sp)
                    
                }
            }
        }
    }
    
}