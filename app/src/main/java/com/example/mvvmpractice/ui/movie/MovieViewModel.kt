package com.example.mvvmpractice.ui.movie

import androidx.lifecycle.*
import androidx.paging.*
import com.example.mvvmpractice.data.MovieDetail
import com.example.mvvmpractice.data.Search
import com.example.mvvmpractice.remote.MovieInterface
import com.example.mvvmpractice.ui.detail.MovieDetailRepository
import com.example.mvvmpractice.utils.Events
import com.example.mvvmpractice.utils.Result
import com.example.mvvmpractice.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieInterface: MovieInterface,
    private val movieDetailRepository: MovieDetailRepository
) : ViewModel() {
    private val query = MutableLiveData("Batman")

    val list: LiveData<PagingData<Search>> = query.switchMap {
        Pager(PagingConfig(pageSize = 10)) {
            MoviePaging(it, movieInterface)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setQuery(s: String) {
        query.postValue(s)
    }

    private val _movieDetails =
        MutableLiveData<Events<Result<MovieDetail>>>()
    val movieDetails: LiveData<Events<Result<MovieDetail>>> =
        _movieDetails

    fun getMovieDetails(imdb: String) = viewModelScope.launch {
        _movieDetails.value = Events(Result.loading("Loading..."))
        _movieDetails.value = Events(movieDetailRepository.getMovieDetails(imdb))
    }


}