package com.example.swapi.data.model

/**
 * Result object for lists
 *
 * count int -- result count.
 * next string -- url for next page of results.
 * previous string -- url for previous page of results.
 * results array -- actual, paged results
 */
data class Results<T>(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<T>)

