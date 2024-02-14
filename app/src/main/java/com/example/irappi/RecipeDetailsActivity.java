package com.example.irappi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irappi.adaptors.IngredientsAdaptor;
import com.example.irappi.listeners.RecipeDetailsListener;
import com.example.irappi.models.RecipeDetailsResponse;
import com.squareup.picasso.Picasso;

public class RecipeDetailsActivity extends AppCompatActivity {

    int id;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recyler_meal_ingredients;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdaptor ingredientsAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));

        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading details...");
        dialog.show();
    }

    private void findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recyler_meal_ingredients = findViewById(R.id.recyler_meal_ingredients);
    }
    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(@Nullable RecipeDetailsResponse response, @Nullable String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.getTitle());
            textView_meal_source.setText(response.getSourceName());
            textView_meal_summary.setText(response.getSummary());
            Picasso.get().load(response.getImage()).into(imageView_meal_image);

            recyler_meal_ingredients.setHasFixedSize(true);
            recyler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
            ingredientsAdaptor = new IngredientsAdaptor(RecipeDetailsActivity.this, response.getExtendedIngredients());
            recyler_meal_ingredients.setAdapter(ingredientsAdaptor);
        }

        @Override
        public void didError(@Nullable String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}