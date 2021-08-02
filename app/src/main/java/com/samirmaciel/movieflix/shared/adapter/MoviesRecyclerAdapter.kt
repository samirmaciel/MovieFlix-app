package com.samirmaciel.movieflix.shared.adapter

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.shared.model.Movie
import com.squareup.picasso.Picasso

class MoviesRecyclerAdapter(val onItemClicked : (Movie, ImageView) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list : MutableList<Movie> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Log.d("RECYCLER", "onBindViewHolder: " + list[position].title.toString())
        when(holder){

            is MyViewHolder -> {
                holder.bind(onItemClicked, list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageMovie = itemView.findViewById<ImageView>(R.id.imageRecyclerPoster)

        fun bind(onItemClicked: (Movie, ImageView) -> kotlin.Unit, movie : Movie){

            Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.poster.toString()).into(imageMovie)


            imageMovie.setOnClickListener{
                onItemClicked(movie, imageMovie)
            }

        }
    }
}