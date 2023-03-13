package com.example.mvvmpractice.ui.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmpractice.data.Search
import com.example.mvvmpractice.remote.MovieInterface
import com.example.mvvmpractice.utils.Constants

class MoviePaging(private val s:String, private val movieInterface: MovieInterface) : PagingSource<Int, Search>() {
    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        val page = params.key?:1

        return  try {

            val data = movieInterface.getAllMovie(s, page, Constants.API_KEY)

            LoadResult.Page(
                data = data.Search,
                prevKey = if(page==1) null else page-1,
                nextKey = if(data.Search.isEmpty()) null else page+1
            )

        }catch (e:Exception){
            LoadResult.Error(e)
        }

    }
}