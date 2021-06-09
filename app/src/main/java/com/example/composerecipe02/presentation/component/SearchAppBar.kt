package com.example.composerecipe02.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composerecipe02.presentation.ui.recipe_list.FoodCategory
import com.example.composerecipe02.presentation.ui.recipe_list.FoodCategoryChip
import com.example.composerecipe02.presentation.ui.recipe_list.getAllFoodCategories

@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onToggleTheme: () -> Unit,
) {


    Surface(
        elevation = 8.dp, modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface
    ) {
        Column {

            Row(modifier = Modifier.fillMaxWidth()) {

                TextField(
                    value = query,
                    onValueChange = { newValue ->
                        onQueryChanged(newValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Button"
                        )
                    },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                    /*onImeActionPerformed={action,
                        softKeyboardController->
                        if (action==ImeAction.Search){
                            viewModel.newSearch(query)
                        }
                    }*/
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onExecuteSearch()
                        },
                    ),

                    )

                ConstraintLayout(modifier = Modifier.align(Alignment.CenterVertically)) {
                    val menu=createRef()
                    IconButton(onClick = onToggleTheme,
                        modifier = Modifier.constrainAs(menu){
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    ) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Toggle Dark/Light Theme")
                    }
                }



            }


            val scrollState = rememberLazyListState()
            LazyRow(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp),
                state = scrollState,
            ) {
                itemsIndexed(items = getAllFoodCategories()) { index, category ->

                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                        }, onExecuteSearch = {
                            onExecuteSearch()
                        }
                    )

                }
            }

        }

    }


}