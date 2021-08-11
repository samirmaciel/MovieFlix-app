package com.samirmaciel.movieflix.shared.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.squareup.picasso.Picasso

class MoviesSliderAdapter(private val mContext : Context) : PagerAdapter() {

    private var mSlidersList : MutableList<MovieEntityApi> = ArrayList()

    override fun getCount(): Int {
        return mSlidersList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.movie_slide_item, null)

        val movieTitle = view.findViewById<TextView>(R.id.movieTitle)
        val movieImage = view.findViewById<ImageView>(R.id.movieImage)

        movieTitle.text = this.mSlidersList[position].title.toString()
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + this.mSlidersList[position].backdrop.toString()).into(movieImage)


        container.addView(view)
        return view


    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun setListSliders(listSliders : MutableList<MovieEntityApi>){
        this.mSlidersList = listSliders
    }

    fun getListSliders() : MutableList<MovieEntityApi>{
        return this.mSlidersList
    }
}