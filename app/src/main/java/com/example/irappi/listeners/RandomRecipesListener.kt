package com.example.irappi.listeners

import com.example.irappi.models.RandomRecipes

interface RandomRecipesListener {
    fun didFetch(response: RandomRecipes?, message: String?)
    fun didError(message: String?)
}
