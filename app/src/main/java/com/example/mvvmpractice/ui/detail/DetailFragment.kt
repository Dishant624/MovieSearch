package com.example.mvvmpractice.ui.detail

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mvvmpractice.MainActivity
import com.example.mvvmpractice.R
import com.example.mvvmpractice.databinding.FragmentDetailBinding
import com.example.mvvmpractice.ui.movie.MovieViewModel
import com.example.mvvmpractice.utils.Status.*
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding : FragmentDetailBinding

    val args: DetailFragmentArgs by navArgs()

    val movieViewModel : MovieViewModel by viewModels()

    var mainToolbar: MaterialToolbar? = null


    private fun enterTransition(): Transition {
        val bounds = ChangeBounds()
        bounds.duration = 2000
        return bounds
    }

    private fun returnTransition(): Transition {
        val bounds = ChangeBounds()
        bounds.interpolator = DecelerateInterpolator()
        bounds.duration = 2000
        return bounds
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedElementEnterTransition = enterTransition()
        sharedElementReturnTransition= returnTransition()
        binding = FragmentDetailBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainToolbar = (requireActivity() as MainActivity).findViewById(R.id.toolbar)
        mainToolbar?.visibility = View.GONE

        binding.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        args.imdbId?.let {
            movieViewModel.getMovieDetails(args.imdbId!!)
        }

        movieViewModel.movieDetails.observe(viewLifecycleOwner){
            when(it.getContentIfNotHandled()?.status){
                SUCCESS -> {
                    binding.movieDetails = it.peekContent().data
                    binding.detailsProgress.visibility = View.GONE
                    binding.contentScroll.visibility = View.VISIBLE
                }
                LOADING -> {
                    binding.detailsProgress.visibility = View.VISIBLE
                    binding.contentScroll.visibility = View.GONE
                }
                ERROR -> {
                    binding.detailsProgress.visibility = View.GONE
                    binding.contentScroll.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        mainToolbar?.visibility = View.VISIBLE
    }
}