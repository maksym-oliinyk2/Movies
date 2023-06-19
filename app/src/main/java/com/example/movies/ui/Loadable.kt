package com.example.movies.ui

data class Loadable<out T>(
    val data: T,
    val loadableState: LoadableState,
) {
    companion object {
        fun <T> newLoadingList(
            data: List<T> = listOf(),
        ): Loadable<List<T>> = Loadable(data = data, loadableState = Loading)

        fun <T> newSingleLoading(
            data: T,
        ): Loadable<T> = Loadable(data = data, loadableState = Loading)
    }
}

sealed interface LoadableState
data class Exception(
    val th: Throwable,
) : LoadableState

object Loading : LoadableState
object Refreshing : LoadableState
object Idle : LoadableState

val Loadable<*>.isLoading: Boolean
    get() = loadableState === Loading
val Loadable<*>.isRefreshing: Boolean
    get() = loadableState === Refreshing
val Loadable<*>.isIdle: Boolean
    get() = loadableState === Idle || isException
val Loadable<*>.isException: Boolean
    get() = loadableState is Exception