package com.example.composerecipe02.network.Responses

import com.example.composerecipe02.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

class RecipeSearchResponse (

    @SerializedName("count")
    var count:Int,
    @SerializedName("results")
    var recipes:List<RecipeDto>,

    )
