package com.samirmaciel.movieflix.shared.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.repository.local.MovieRepositoryLocal
import com.squareup.picasso.Picasso

class MoviesRecyclerAdapterApi(val onItemClicked : (MovieEntityApi) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list : MutableList<MovieEntityApi> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


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

        fun bind(onItemClicked: (MovieEntityApi) -> kotlin.Unit, movieEntityApi : MovieEntityApi){

            Picasso.get().load("https://image.tmdb.org/t/p/w500" + movieEntityApi.poster.toString()).into(imageMovie)


            imageMovie.setOnClickListener{
                onItemClicked(movieEntityApi)
            }

        }
    }
}