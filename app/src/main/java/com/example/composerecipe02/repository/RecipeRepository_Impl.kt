package com.example.composerecipe02.repository

import com.example.composerecipe02.domain.model.Recipe
import com.example.composerecipe02.network.RecipeService
import com.example.composerecipe02.network.model.RecipeDtoMapper
import java.lang.reflect.Constructor
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
/** OR WE CAN TRY ANOTHER WAY, CHECK RepositoryModule.kt*/
class RecipeRepository_Impl(
    //@Inject
    //constructor(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
):RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        val result=recipeService.search(token, page, query).recipes
        return mapper.toDomainList(result)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(recipeService.get(token, id))
    }
}