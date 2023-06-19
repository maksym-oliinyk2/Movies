package com.example.movies.domain

import arrow.core.Either
import com.example.movies.domain.model.MoviePreview

class MoviesUseCase(
    private val repository: AppRepository,
) {
    suspend fun fetchMoviePreviews(): Either<Throwable, List<MoviePreview>> = repository.fetchMoviePreviews()
}