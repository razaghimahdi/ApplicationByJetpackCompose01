package com.example.composerecipe02.repository

import com.example.composerecipe02.domain.model.Recipe

interface RecipeRepository {
    suspend fun search(token:String,page:Int,query: String):List<Recipe>
    suspend fun get(token: String,id:Int):Recipe
}