package com.example.movies.domain

import arrow.core.Either
import com.example.movies.data.MoviePreviewResponse
import com.example.movies.data.MovieResponse
import com.example.movies.domain.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.URI

class AppRepositoryImpl(
    loggingLevel: LogLevel,
) : AppRepository {

    private companion object {
        val BaseUrl = Url("https://us-central1-temporary-692af.cloudfunctions.net")
    }

    private val client by lazy { HttpClient(loggingLevel) }

    override suspend fun fetchMoviePreviews(): Either<Throwable, List<MoviePreview>> = withContext(Dispatchers.IO) {
        Either.catch {
            client.get(
                HttpRequestBuilder(
                    scheme = URLProtocol.HTTPS.name,
                    host = BaseUrl.host,
                    path = "/movies"
                )
            ).body<List<MoviePreviewResponse>>()
        }.map { response ->
            response.map(MoviePreviewResponse::toEntity)
        }
    }

    override suspend fun fetchMovie(
        id: Id,
    ): Either<Throwable, Movie> = withContext(Dispatchers.IO) {
        Either.catch {
            client.get(
                HttpRequestBuilder(
                    scheme = URLProtocol.HTTPS.name,
                    host = BaseUrl.host,
                    path = "/movieDetails"
                ) {
                    parameters.append("id", id.value)
                }
            ).body<MovieResponse>()
        }.map { response ->
            response.toEntity(id)
        }
    }

}

private fun HttpClient(
    level: LogLevel,
) = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(
            json = Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                isLenient = true
            }
        )

        Logging {
            this.level = level
        }
    }
}

private fun MovieResponse.toEntity(
    id: Id,
): Movie = Movie(
    id = id,
    image = runCatching { URI(image) }.getOrNull(),
    metadata = Metadata(meta),
    name = Name(name),
    price = Price(price),
    rating = Rating(rating),
    synopsis = Synopsis(synopsis)
)

private fun MoviePreviewResponse.toEntity(): MoviePreview =
    MoviePreview(
        id = Id(id),
        name = Name(name),
        price = Price(price)
    )