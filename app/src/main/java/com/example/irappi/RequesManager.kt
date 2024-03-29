package com.example.irappi

import android.content.Context
import com.example.irappi.listeners.RandomRecipesListener
import com.example.irappi.models.RandomRecipes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RequestManager(var context: Context) {
    var retrofit = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getRandomRecipes(listener: RandomRecipesListener) {
        val callRandomRecipes = retrofit.create(
            CallRandomRecipes::class.java
        )
        val call = callRandomRecipes.callRandomRecipe(
            context.getString(R.string.api_key), "10"
        )
        call.enqueue(object : Callback<RandomRecipes?> {
            override fun onResponse(
                call: Call<RandomRecipes?>,
                response: Response<RandomRecipes?>
            ) {
                if (!response.isSuccessful) {
                    listener.didError(response.message())
                    return
                }
                listener.didFetch(response.body(), response.message())
            }

            override fun onFailure(call: Call<RandomRecipes?>, t: Throwable) {
                listener.didError(t.message)
            }
        })
    }

    private interface CallRandomRecipes {
        @GET("recipes/random")
        fun callRandomRecipe(
            @Query("apiKey") apiKey: String?,
            @Query("number") number: String?
        ): Call<RandomRecipes>
    }
}
