package com.example.composerecipe02.di

import com.example.composerecipe02.network.RecipeService
import com.example.composerecipe02.network.model.RecipeDtoMapper
import com.example.composerecipe02.presentation.MainActivity.Companion.BASE_URL
import com.example.composerecipe02.presentation.MainActivity.Companion.TOKEN
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideRecipeMapper(): RecipeDtoMapper{
        return RecipeDtoMapper()
    }

    @Singleton
    @Provides
    fun provideRecipeService():RecipeService{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeService::class.java)
    }


    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken():String{
        return TOKEN
    }



}