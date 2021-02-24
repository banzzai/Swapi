package com.example.swapi.data.dao

import com.example.swapi.data.model.Species
import com.example.swapi.data.model.Results
import com.example.swapi.utils.retrofit.ApiService

/**
 * SpeciesSwapiDao
 *
 * Data access object for Species metadata.
 *
 * This DAO will be used to centralize Swapi API calls, and mirror an implementation we might have
 * eventually, using database or memory caching.
 */
class SpeciesSwapiDao(private val apiService: ApiService) {
    /**
     * Retrieve all Species, given a page number
     *
     * @param page the page number
     */
    suspend fun getSpecies(page: Int): Results<Species> {
        return apiService.getSpecies(page)
    }

    /**
     * Search for Species, given a string and a result page number
     *
     * @param page the page number
     * @param searchString search string to match with name field
     */
    suspend fun searchSpecies(page: Int, searchString: String): Results<Species> {
        return apiService.getSpeciesSearchPage(searchString, page)
    }
    
    /**
     * Retrieve a Species object, given its direct url
     *
     * @param speciesUrl Url to the specific Species entry
     */
    suspend fun getSpeciesByUrl(speciesUrl: String): Species {
        return apiService.getSpeciesByUrl(speciesUrl)
    }
}