package dev.senk0n.moerisuto.core.model

enum class Season { Winter, Spring, Summer, Autumn }

fun Date.toSeason(): Season? = when (month) {
    12, 1, 2 -> Season.Winter
    3, 4, 5 -> Season.Spring
    6, 7, 8 -> Season.Summer
    9, 10, 11 -> Season.Autumn
    else -> null
}