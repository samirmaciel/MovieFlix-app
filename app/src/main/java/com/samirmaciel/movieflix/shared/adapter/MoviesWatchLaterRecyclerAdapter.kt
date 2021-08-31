package com.samirmaciel.movieflix.shared.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.shared.model.local.MovieWatchLaterEntityLocal
import com.samirmaciel.movieflix.shared.model.local.MovieWatchedEntityLocal
import com.squareup.picasso.Picasso

class MoviesWatchLaterRecyclerAdapter(val onButtonClick : (Int, MovieWatchLaterEntityLocal) -> Unit, val onItemClick : (MovieWatchLaterEntityLocal) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var list : MutableList<MovieWatchLaterEntityLocal> = arrayListOf()


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
        val realeseDate = itemView.findViewById<TextView>(R.id.realeseMovieDateText)
        val titleMovie = itemView.findViewById<TextView>(R.id.movieTextName_mylist)
        val rateMovie = itemView.findViewById<TextView>(R.id.movieRate_mylist)
        val buttonRemove = itemView.findViewById<ImageView>(R.id.buttonRemove)

        fun bind(
            onButtonClick: (Int, MovieWatchLaterEntityLocal) -> Unit,
            onItemClick: (MovieWatchLaterEntityLocal) -> Unit,
            movie: MovieWatchLaterEntityLocal
        ) {

            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + movie.poster.toString())
                .into(imageMovie)
            titleMovie.text = movie.title
            rateMovie.text = movie.voteAverage
            realeseDate.text = movie.realese.split("-")[0]


            imageMovie.setOnClickListener{
                onItemClick(movie)
            }

            buttonRemove.setOnClickListener {
                onButtonClick(adapterPosition, movie)
            }
        }
    }


}