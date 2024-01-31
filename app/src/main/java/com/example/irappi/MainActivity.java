package com.example.irappi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irappi.adaptors.RandomRecipeAdapter;
import com.example.irappi.listeners.RandomRecipesListener;
import com.example.irappi.models.RandomRecipes;

public class MainActivity  extends AppCompatActivity {
    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Carregando...");

        manager = new RequestManager(this);
        manager.getRandomRecipes(randomRecipesListener);
        dialog.show();
    }
    private final RandomRecipesListener randomRecipesListener = new RandomRecipesListener() {
        @Override
        public void didFetch(@Nullable RandomRecipes response, @Nullable String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response.getRecipes());
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(@Nullable String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}
