package com.example.irappi;

import android.content.Context;

import com.example.irappi.listeners.RandomRecipesListener;
import com.example.irappi.models.RandomRecipes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipesListener listener) {
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipes> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "10");
        call.enqueue(new Callback<RandomRecipes>() {
            @Override
            public void onResponse(Call<RandomRecipes> call, Response<RandomRecipes> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipes> call, Throwable t) {
                listener.didError(  t.getMessage());
            }
        });
    }
    private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RandomRecipes> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number
        );
    }
}
