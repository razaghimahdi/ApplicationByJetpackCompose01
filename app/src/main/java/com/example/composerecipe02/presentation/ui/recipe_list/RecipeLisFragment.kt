package com.example.composerecipe02.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.composerecipe02.presentation.BaseApplication
import com.example.composerecipe02.presentation.component.*
import com.example.composerecipe02.presentation.component.util.SnackbarController
import com.example.composerecipe02.presentation.ui.theme.ComposeRecipe02Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeLisFragment : Fragment() {

    val TAG = "RecipeLisFragment"

    @Inject
    lateinit var application: BaseApplication

    private val snackbarController = SnackbarController(lifecycleScope)


    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                // snackbarTest()


                ComposeRecipe02Theme(darkTheme = application.isDark.value) {

                    val recipes = viewModel.recipes.value

                    /*val query = remember {
                        mutableStateOf("beef")
                    }*/

                    val query = viewModel.query.value
                    //val _query=savedInstanceState{"beef"}

                    val selectedCategory = viewModel.selectedCategory.value

                    val loading = viewModel.loading.value

                    val scaffoldState = rememberScaffoldState()

                    val page=viewModel.page.value

                    for (recipe in recipes) {
                        Log.i(TAG, "onCreateView: ")
                    }


                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    if (viewModel.selectedCategory.value?.value == "Milk") {
                                        snackbarController.getScope().launch {
                                            snackbarController
                                                .showSnackbar(
                                                    scaffoldState = scaffoldState,
                                                    message = "Invalid category!",
                                                    actionLabel = "Hide"
                                                )
                                        }
                                    } else {
                                        viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                    }
                                },
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onToggleTheme = {
                                    application.toggleLightTheme()
                                }
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState },
                        //bottomBar = { MyBottomBar() },
                        //drawerContent = { MyDrawer() }
                    ) {

                        if (loading ) {

                            if (recipes.isEmpty()) {
                                LoadingRecipeListShimmer(imageHeight = 250.dp)
                            }else{
                                CircularIndeterminateProgressBar(isDisplayed = loading)
                            }

                        } else {

                            RecipeList(
                                loading = loading,
                                recipes = recipes,
                                onChangeRecipeScrollPosition = { viewModel::onChangeCategoryScrollPosition},
                                page = page,
                                onNextPage = { viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent) },
                                navController = findNavController()
                            )

                        }

                    }


                }


            }
        }
    }

    @Composable
    private fun snackbarTest() {

        val isShowing = remember { mutableStateOf(false) }
        SnackbarDemo(
            isShowing = isShowing.value,
            onHideSnackbar = { isShowing.value = false })

        val snackbarHostState = remember { SnackbarHostState() }

        Column {
            Button(onClick = {
                //isShowing.value = true
                lifecycleScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Hey this is a snackbar",
                        actionLabel = "Hide",
                        duration = SnackbarDuration.Short
                    )
                }
            }) {
                Text("Show snackbar")
            }
        }
        SnackbarDemo2(snackbarHostState = snackbarHostState)

    }

    /* override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         val view = inflater.inflate(R.layout.fragment_recipe_list,container,false)
         view.findViewById<ComposeView>(R.id.compose_view).setContent {
             Text(text = "THIS IS A COMPOSABLE INSIDE THE FRAGMENT")

             Spacer(modifier = Modifier.padding(10.dp))

             CircularProgressIndicator()

             Spacer(modifier = Modifier.padding(10.dp))

             Text(text = "NEAT")

             Spacer(modifier = Modifier.padding(10.dp))

             val customView = HorizontalDottedProgress(LocalContext.current)
             AndroidView(factory = {customView})
         }
         return view
     }*/

}

@Composable
fun SnackbarDemo2(
    snackbarHostState: SnackbarHostState
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackbar = createRef()
        SnackbarHost(modifier = Modifier.constrainAs(snackbar) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(action = {
                    TextButton(
                        onClick = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }
                    ) {
                        Text(
                            text = snackbarHostState.currentSnackbarData?.actionLabel ?: "Hide",
                            style = TextStyle(color = Color.White)
                        )
                    }
                }) {
                    Text(
                        snackbarHostState.currentSnackbarData?.message ?: "Hey there is a snackbar"
                    )

                }
            }
        )
    }

}


@Composable
fun SnackbarDemo(
    isShowing: Boolean,
    onHideSnackbar: () -> Unit,
) {
    if (isShowing) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val snackbar = createRef()
            Snackbar(modifier = Modifier.constrainAs(snackbar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
                action = {
                    Text(
                        "Hide",
                        modifier = Modifier.clickable(onClick = onHideSnackbar),
                        style = MaterialTheme.typography.h5
                    )
                }) {
                Text("Hey there is a snackbar")
            }
        }
    }

}


@Composable
fun MyBottomBar() {
    BottomNavigation(elevation = 12.dp) {
        BottomNavigationItem(selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.BrokenImage, contentDescription = "") }
        )
        BottomNavigationItem(selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Search, contentDescription = "") }
        )
        BottomNavigationItem(selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = "") }
        )
    }
}


@Composable
fun MyDrawer() {
    Column {
        Text("Item1")
        Text("Item2")
        Text("Item3")
        Text("Item4")
    }
}


@Composable
fun GradientDemo() {
    val colors = listOf(
        Color.Blue,
        Color.Red,
        Color.Blue,
    )
    val brush = linearGradient(
        colors,
        start = Offset(200f, 200f),
        end = Offset(400f, 400f)
    )

    Surface(shape = MaterialTheme.shapes.small) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
        )
    }

}