package com.example.irappi.listeners;

import com.example.irappi.models.RandomRecipes;

public interface RandomRecipesListener {
    void didFetch(RandomRecipes response, String message);
    void didError(String message);
}
