package com.example.mvvmpractice.remote

import com.example.mvvmpractice.data.MovieDetail
import com.example.mvvmpractice.data.MovieResponse
 import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInterface {

    @GET("/")
    suspend fun getAllMovie(
        @Query ("s")s:String,
        @Query ("page")page:Int,
        @Query ("apiKey")apiKey:String,
    ):MovieResponse

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i")  imdbId : String = "tt0096895",
        @Query("apiKey") apiKey: String
    ) : MovieDetail
}