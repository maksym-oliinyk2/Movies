package com.example.movies.domain.model

import java.net.URI

data class Movie(
    val id: Id,
    val image: URI?,
    val metadata: Metadata,
    val name: Name,
    val price: Price,
    val rating: Rating,
    val synopsis: Synopsis,
)

data class MoviePreview(
    val id: Id,
    val name: Name,
    val price: Price,
)

@JvmInline
value class Id(
    val value: String,
) {
    init {
        require(value.isNotBlank())
    }
}

@JvmInline
value class Metadata(
    val value: String,
) {
    init {
        require(value.isNotBlank())
    }
}

@JvmInline
value class Name(
    val value: String,
) {
    init {
        require(value.isNotBlank())
    }
}

@JvmInline
value class Price(val value: Int) {
    init {
        require(value >= 0)
    }
}

@JvmInline
value class Rating(
    val value: String,
) {
    init {
        require(value.isNotBlank())
    }
}

@JvmInline
value class Synopsis(
    val value: String,
) {
    init {
        require(value.isNotBlank())
    }
}