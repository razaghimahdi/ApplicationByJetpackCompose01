package com.example.composerecipe02.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.composerecipe02.presentation.BaseApplication
import com.example.composerecipe02.presentation.component.CircularIndeterminateProgressBar
import com.example.composerecipe02.presentation.component.IMAGE_HEIGHT
import com.example.composerecipe02.presentation.component.LoadingRecipeShimmer
import com.example.composerecipe02.presentation.component.RecipeView
import com.example.composerecipe02.presentation.component.util.SnackbarController
import com.example.composerecipe02.presentation.ui.theme.ComposeRecipe02Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: RecipeViewModel by viewModels()

    private var recipeId: MutableState<Int> = mutableStateOf(-1)

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


        arguments?.getInt("recipeId")?.let { recipeId ->
            this.recipeId.value = recipeId
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


                val scaffoldState = rememberScaffoldState()

                ComposeRecipe02Theme(darkTheme = application.isDark.value) {
                    Scaffold(scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState }) {
                        Box(modifier = Modifier.fillMaxSize()){


                            if (loading && recipe == null){
                                Text(text = "Lading...")
                                LoadingRecipeShimmer(imageHeight = IMAGE_HEIGHT.dp,)
                            }else{
                                recipe?.let {
                                    if (it.id==1){
                                        snackbarController.showSnackbar(
                                            scaffoldState = scaffoldState,
                                            message = "An error occuerd with this recipe",actionLabel = "OK"
                                        )
                                    }else{
                                        RecipeView(recipe = it)
                                    }
                                }
                            }

                            CircularIndeterminateProgressBar(isDisplayed = loading)

                        }

                    }
                }


            }
        }
    }

}