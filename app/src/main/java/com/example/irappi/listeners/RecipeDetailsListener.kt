package com.example.irappi.listeners

import com.example.irappi.models.RecipeDetailsResponse

interface RecipeDetailsListener {
    fun didFetch(response: RecipeDetailsResponse?, message: String?)
    fun didError(message: String?)
}
