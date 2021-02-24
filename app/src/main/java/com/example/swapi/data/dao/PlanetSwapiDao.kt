package com.example.swapi.data.dao

import com.example.swapi.data.model.Planet
import com.example.swapi.data.model.Results
import com.example.swapi.utils.retrofit.ApiService

/**
 * PlanetSwapiDao
 *
 * Data access object for Planet metadata.
 *
 * This DAO will be used to centralize Swapi API calls, and mirror an implementation we might have
 * eventually, using database or memory caching.
 */
class PlanetSwapiDao(private val apiService: ApiService) {
    /**
     * Retrieve all planets, given a page number
     *
     * @param page the page number
     */
    suspend fun getPlanet(page: Int): Results<Planet> {
        return apiService.getPlanet(page)
    }

    /**
     * Search for a planet, given a string and a result page number
     *
     * @param page the page number
     * @param searchString search string to match with name field
     */
    suspend fun searchPlanet(page: Int, searchString: String): Results<Planet> {
        return apiService.getPlanetSearchPage(searchString, page)
    }

    /**
     * Retrieve a Planet object, given its direct url
     *
     * @param planetUrl Url to the specific Planet entry
     */
    suspend fun getPlanetByUrl(planetUrl: String): Planet {
        return apiService.getPlanetByUrl(planetUrl)
    }
}