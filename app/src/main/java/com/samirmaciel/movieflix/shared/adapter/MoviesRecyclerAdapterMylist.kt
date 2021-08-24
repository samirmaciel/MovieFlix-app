package com.samirmaciel.movieflix.shared.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.shared.model.local.MovieEntityLocal
import com.squareup.picasso.Picasso

class MoviesRecyclerAdapterMylist(val onButtonClick : (Int, MovieEntityLocal) -> Unit, val onItemClick : (MovieEntityLocal) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var list : MutableList<MovieEntityLocal> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.mylist_item_recyclerview, parent, false), parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when(holder){

            is MyViewHolder -> {
                holder.bind(onButtonClick, onItemClick, list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }



    inner class MyViewHolder(itemView: View, val parent : ViewGroup) : RecyclerView.ViewHolder(itemView) {

        val imageMovie = itemView.findViewById<ImageView>(R.id.movieImage_mylist)
        val titleMovie = itemView.findViewById<TextView>(R.id.movieTextName_mylist)
        val rateMovie = itemView.findViewById<TextView>(R.id.movieRate_mylist)
        val buttonRemove = itemView.findViewById<ImageView>(R.id.buttonRemove)

        fun bind(
            onButtonClick: (Int, MovieEntityLocal) -> Unit,
            onItemClick: (MovieEntityLocal) -> Unit,
            movieEntityLocal: MovieEntityLocal
        ) {

            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + movieEntityLocal.poster.toString())
                .into(imageMovie)
            titleMovie.text = movieEntityLocal.title
            rateMovie.text = movieEntityLocal.voteAverage

            buttonRemove.setOnClickListener {
                onButtonClick(adapterPosition, movieEntityLocal)
            }
        }
    }


}