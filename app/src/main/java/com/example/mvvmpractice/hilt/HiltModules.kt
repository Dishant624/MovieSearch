package com.example.mvvmpractice.hilt

import com.example.mvvmpractice.remote.MovieInterface
import com.example.mvvmpractice.ui.detail.MovieDetailRepository
import com.example.mvvmpractice.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HiltModules  {

    @Singleton
    @Provides
    fun provideRetrofitInterface() : MovieInterface{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(MovieInterface::class.java)
    }

    @Provides
    fun provideRepository(movieInterface: MovieInterface): MovieDetailRepository {
        return MovieDetailRepository(movieInterface)
    }

}