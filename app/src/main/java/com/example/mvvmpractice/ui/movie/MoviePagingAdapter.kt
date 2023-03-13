package com.example.mvvmpractice.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmpractice.BR
import com.example.mvvmpractice.data.Search
import com.example.mvvmpractice.databinding.ViewHolderMovieBinding

class MoviePagingAdapter : PagingDataAdapter<Search, MoviePagingAdapter.ViewHolder>(DIFF_UTIL) {

    var onclick :((String, FragmentNavigator.Extras) -> Unit)?= null

    companion object{
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Search>(){
            override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
                return  oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem == newItem
            }

        }
    }

    fun onMovieClick(listener : (String, FragmentNavigator.Extras) ->Unit){
        onclick = listener
    }

    override fun onBindViewHolder(holder: MoviePagingAdapter.ViewHolder, position: Int) {

        val movie: Search? = getItem(position)

        holder.viewDataBinding.setVariable(BR.movie, getItem(position))
        holder.viewDataBinding.root.setOnClickListener {
            onclick?.let {
                val extra = FragmentNavigator.Extras.Builder()
                    .addSharedElement(holder.viewDataBinding.movieImage,"movieImage")
                    .addSharedElement(holder.viewDataBinding.movieTitle,"movieTitle")
                    .build()
                it(movie?.imdbID!!,extra)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviePagingAdapter.ViewHolder {
        val binding = ViewHolderMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val viewDataBinding: ViewHolderMovieBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

    }
}