package com.example.movies.app

sealed interface Command

sealed interface MoviesCommand : Command

object DoLoadMovies : MoviesCommand