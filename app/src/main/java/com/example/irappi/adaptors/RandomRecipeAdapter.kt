package com.example.irappi.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.irappi.R
import com.example.irappi.models.Recipe
import com.squareup.picasso.Picasso

class RandomRecipeAdapter(var context: Context, var list: List<Recipe>) :
    RecyclerView.Adapter<RandomRecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomRecipeViewHolder {
        return RandomRecipeViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RandomRecipeViewHolder, position: Int) {
        val servings = list[position].servings
        val minutes = list[position].readyInMinutes
        val likes = list[position].aggregateLikes
        holder.textView_title.text = list[position].title
        holder.textView_title.isSelected = true
        holder.textView_likes.text = String.format("%s curtidas", likes)
        holder.textView_servings.text = String.format("%s pessoas", servings)
        holder.textView_time.text = String.format("%s minutos", minutes)
        Picasso.get().load(list[position].image).into(holder.imageView_food)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class RandomRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var random_list_container: CardView
    var textView_title: TextView
    var textView_servings: TextView
    var textView_likes: TextView
    var textView_time: TextView
    var imageView_food: ImageView

    init {
        random_list_container = itemView.findViewById(R.id.random_list_container)
        textView_title = itemView.findViewById(R.id.textView_title)
        textView_servings = itemView.findViewById(R.id.textView_servings)
        textView_likes = itemView.findViewById(R.id.textView_likes)
        textView_time = itemView.findViewById(R.id.textView_time)
        imageView_food = itemView.findViewById(R.id.imageView_food)
    }
}