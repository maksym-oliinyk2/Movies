package com.example.movies.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviePreviewResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("price") val price: Int,
)

@Serializable
data class MovieResponse(
    @SerialName("image") val image: String,
    @SerialName("meta") val meta: String,
    @SerialName("name") val name: String,
    @SerialName("price") val price: Int,
    @SerialName("rating") val rating: String,
    @SerialName("synopsis") val synopsis: String,
)