package com.example.swapi.data.dao

import com.example.swapi.data.model.Starship
import com.example.swapi.utils.retrofit.ApiService

/**
 * StarshipSwapiDao
 *
 * Data access object for Planet metadata.
 *
 * This DAO will be used to centralize Swapi API calls, and mirror an implementation we might have
 * eventually, using database or memory caching.
 */
class StarshipSwapiDao(private val apiService: ApiService) {
    /**
     * Retrieve a Starship object, given its direct url
     *
     * @param starshipUrl Url to the specific Starship entry
     */
    suspend fun getStarshipByUrl(starshipUrl: String): Starship {
        return apiService.getStarshipByUrl(starshipUrl)
    }
}