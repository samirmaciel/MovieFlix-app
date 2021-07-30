package com.samirmaciel.movieflix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.samirmaciel.movieflix.databinding.ActivityMainBinding
import com.samirmaciel.movieflix.modules.home.HomeFragment
import com.samirmaciel.movieflix.shared.model.Movie
import com.samirmaciel.movieflix.shared.repository.MovieRepositoryInterface
import com.samirmaciel.movieflix.shared.data.MovieApiService
import com.samirmaciel.movieflix.shared.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, HomeFragment())
                .commitAllowingStateLoss()
        }

    }

    override fun onStart() {
        super.onStart()




    }

    private fun getMovieData(callback: (List<Movie>) -> Unit){

        val apiMovies = MovieApiService.getInstance().create(MovieRepositoryInterface::class.java)

        apiMovies.getPopularMovies().enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }
        })

    }
}