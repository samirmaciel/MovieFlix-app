package com.samirmaciel.movieflix.shared.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.shared.model.api.ActorModel
import com.squareup.picasso.Picasso

class ActorsRecyclerAdapter(val onItemClick : (ActorModel) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list : MutableList<ActorModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cast_recycler_item, parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MyViewHolder ->{
                holder.bind(onItemClicked = onItemClick, list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val actorImage = itemView.findViewById<ImageView>(R.id.castRecyclerImage)
        val actorName = itemView.findViewById<TextView>(R.id.actorNameText)

        fun bind(onItemClicked : (ActorModel) -> Unit, item : ActorModel){

            Picasso.get().load("https://image.tmdb.org/t/p/w500" + item.profile_path).into(actorImage)
            actorName.text = item.name
            actorImage.setOnClickListener{
                onItemClicked(item)
            }
        }

    }
}