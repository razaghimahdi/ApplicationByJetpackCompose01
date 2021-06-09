package com.example.composerecipe02.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun CircularIndeterminateProgressBar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {



        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (progressBar, text) = createRefs()
            val topGuidLine = createGuidelineFromTop(0.3f)

            CircularProgressIndicator(modifier = Modifier.constrainAs(progressBar)
            {
                //top.linkTo(parent.top)
                //bottom.linkTo(parent.bottom)
                top.linkTo(topGuidLine)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
                color = MaterialTheme.colors.primary
            )

            Text(text = "Loading...",style = TextStyle(color = Color.Black),modifier = Modifier.constrainAs(text){
                top.linkTo(progressBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })


        }


    }

}

private fun myDecoupledConstraints(verticaBias:Float):ConstraintSet{
    return ConstraintSet {
        val guideline=createGuidelineFromTop(verticaBias)
        val progressBar = createRefFor("progressBar")
        val text=createRefFor("text")

        constrain(progressBar){
            top.linkTo(guideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(text){
            top.linkTo(progressBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }


    }
}