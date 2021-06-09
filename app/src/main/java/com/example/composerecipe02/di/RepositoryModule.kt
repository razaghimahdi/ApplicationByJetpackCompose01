package com.example.composerecipe02.di

import com.example.composerecipe02.network.RecipeService
import com.example.composerecipe02.network.model.RecipeDtoMapper
import com.example.composerecipe02.repository.RecipeRepository
import com.example.composerecipe02.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService:RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ):RecipeRepository{
        return RecipeRepository_Impl(recipeService,recipeDtoMapper)
    }


}