package com.samirmaciel.movieflix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.samirmaciel.movieflix.shared.model.Movie
import com.samirmaciel.movieflix.shared.model.MovieApiInterface
import com.samirmaciel.movieflix.shared.model.MovieApiService
import com.samirmaciel.movieflix.shared.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        var list = MutableLiveData<List<Movie>>()

        getMovieData { movies : List<Movie> ->
            list.postValue(movies)
        }

        list.observe(this, Observer {
            for (m in it){
                Log.d("VAI", "title: " + m.title)
            }
        })

    }

    private fun getMovieData(callback: (List<Movie>) -> Unit){

        val apiMovies = MovieApiService.getInstance().create(MovieApiInterface::class.java)

        apiMovies.getMovies().enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }
        })

    }
}