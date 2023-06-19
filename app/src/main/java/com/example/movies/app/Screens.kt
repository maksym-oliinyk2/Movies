package com.example.movies.app

import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MoviePreview
import com.example.movies.ui.Loadable

// todo filters
data class MoviesScreen(
    val loadable: Loadable<List<MoviePreview>> = Loadable.newLoadingList(),
) : Screen

data class DetailsScreen(
    val loadable: Loadable<Movie?>,
) : Screen