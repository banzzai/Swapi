package com.example.swapi.data.dao

import com.example.swapi.data.model.People
import com.example.swapi.data.model.Results
import com.example.swapi.utils.retrofit.ApiService

/**
 * PeopleSwapiDao
 *
 * Data access object for lists of People and People metadata.
 *
 * This DAO will be used to centralize Swapi API calls, and mirror an implementation we might have
 * eventually, using database or memory caching.
 */
class PeopleSwapiDao(private val apiService: ApiService) {
    /**
     * Retrieve all people, given a page number
     *
     * @param page the page number
     */
    suspend fun getPeople(page: Int): Results<People> {
        return apiService.getPeople(page)
    }

    /**
     * Search for people, given a string and a result page number
     *
     * @param page the page number
     * @param searchString search string to match with name field
     */
    suspend fun searchPeople(page: Int, searchString: String): Results<People> {
        return apiService.getPeopleSearchPage(searchString, page)
    }

    /**
     * Retrieve a People object, given its direct url
     *
     * @param peopleUrl Url to the specific People entry
     */
    suspend fun getPeopleByUrl(peopleUrl: String): People {
        return apiService.getPeopleByUrl(peopleUrl)
    }
}