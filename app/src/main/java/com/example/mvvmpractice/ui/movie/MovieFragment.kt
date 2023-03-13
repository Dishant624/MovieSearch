package com.example.mvvmpractice.ui.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvvmpractice.databinding.FragmentMovieBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvmpractice.MainActivity
import com.example.mvvmpractice.R
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment() {

    lateinit var binding: FragmentMovieBinding

    val movieViewModel: MovieViewModel by viewModels()

    lateinit var moviePagingAdapter: MoviePagingAdapter

    var mSliderState: Boolean = false

    var mainToolbar: MaterialToolbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("DiscouragedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        val navigationDrawer = (requireActivity() as MainActivity).findViewById<DrawerLayout>(R.id.drawerLayout)
        mainToolbar = (requireActivity() as MainActivity).findViewById(R.id.toolbar)
        mainToolbar?.visibility = View.GONE


        navigationDrawer?.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {
                mSliderState = true
            }

            override fun onDrawerClosed(drawerView: View) {
                mSliderState = false
            }

            override fun onDrawerStateChanged(newState: Int) {

            }

        })

        binding.menu.setOnClickListener {
            if (mSliderState) {
                navigationDrawer?.closeDrawer(GravityCompat.START)
            } else {
                navigationDrawer?.openDrawer(GravityCompat.START)
            }
        }


        binding.movieSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    movieViewModel.setQuery(it)
                }
                lifecycleScope.launch {
                    delay(1000L)
                    binding.movieSearch.isIconified = true
                    binding.movieSearch.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


        moviePagingAdapter.onMovieClick { s, extras ->
            val action = MovieFragmentDirections.actionMovieFragmentToDetailFragment(s)
            findNavController().navigate(action, extras)
        }

        movieViewModel.list.observe(viewLifecycleOwner) {
            moviePagingAdapter.submitData(lifecycle, it)
        }

    }

    override fun onPause() {
        super.onPause()
        mainToolbar?.visibility = View.VISIBLE

    }

    private fun setRecyclerView() {
        binding.movieRecyclerView.apply {
            moviePagingAdapter = MoviePagingAdapter()
            adapter = moviePagingAdapter
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

        }
    }
}