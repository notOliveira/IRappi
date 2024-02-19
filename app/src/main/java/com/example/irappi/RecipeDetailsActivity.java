package com.example.irappi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irappi.adaptors.IngredientsAdaptor;
import com.example.irappi.listeners.RecipeDetailsListener;
import com.example.irappi.models.RecipeDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class RecipeDetailsActivity extends AppCompatActivity {

    int id;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary, textView_meal_id;
    ImageView imageView_meal_image;
    RecyclerView recyler_meal_ingredients;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdaptor ingredientsAdaptor;

    private String removeHtml(String html) {
        return html.replaceAll("<[^>]*>", "");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViews();


        this.id = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("id")));

        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading details...");
        dialog.show();
    }

    private void findViews() {
        textView_meal_id = findViewById(R.id.textView_meal_id);
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
            textView_meal_id.setText(String.format("ID: #" + response.getId()));
            textView_meal_name.setText(response.getTitle());
            textView_meal_source.setText(String.format("Source" + response.getSourceName()));
            String plain_text = removeHtml(String.valueOf(Html.fromHtml(response.getSummary())));
            textView_meal_summary.setText(plain_text);
            Picasso.get().load(response.getImage()).into(imageView_meal_image);

            int numberOfColumns = 2;
            recyler_meal_ingredients.setHasFixedSize(true);
            GridLayoutManager layoutManager = new GridLayoutManager(RecipeDetailsActivity.this, numberOfColumns);
            recyler_meal_ingredients.setLayoutManager(layoutManager);
            ingredientsAdaptor = new IngredientsAdaptor(RecipeDetailsActivity.this, response.getExtendedIngredients());
            recyler_meal_ingredients.setAdapter(ingredientsAdaptor);
        }

        @Override
        public void didError(@Nullable String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }


    };
}