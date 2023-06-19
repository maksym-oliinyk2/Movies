package com.example.movies.app

typealias NavigationStack = List<Screen>

data class AppState(
    val screens: NavigationStack,
) {

    constructor(screen: Screen) : this(listOf(screen))

    init {
        require(screens.isNotEmpty())
    }
}

inline val AppState.screen: Screen
    get() = screens.last()

sealed interface Screen
