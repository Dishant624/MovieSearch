package com.example.mvvmpractice.ui.detail

import com.example.mvvmpractice.data.MovieDetail
import com.example.mvvmpractice.remote.MovieInterface
import com.example.mvvmpractice.utils.Constants
import com.example.mvvmpractice.utils.Status

class MovieDetailRepository(private val movieInterface: MovieInterface) {

        suspend fun getMovieDetails(imdbId : String):com.example.mvvmpractice.utils.Result<MovieDetail>{
            return try {
                val movieDetail = movieInterface.getMovieDetails(imdbId, apiKey = Constants.API_KEY)
                com.example.mvvmpractice.utils.Result.success(movieDetail)
            }catch (e:Exception){
                com.example.mvvmpractice.utils.Result.error(e.message)
            }
        }
}