package com.example.swapi.data.repositories

import com.example.swapi.data.dao.PeopleSwapiDao
import com.example.swapi.data.model.People

/**
 * PeopleRepository will have ownership over People data
 *
 * It would be utilized to triage and balance between API calls, local database and/or other means
 * of caching we probably won't be implementing in this first version...
 *
 * It will also serve to centralize dependencies for testing purpose (we will see if I get the time
 * to do some of that in v1)
 */
class PeopleRepository private constructor(private val peopleDao: PeopleSwapiDao) {

    companion object {
        @Volatile private var _instance: PeopleRepository? = null

        fun getInstance(peopleDao: PeopleSwapiDao) =
            _instance ?: synchronized(this) {
                _instance ?: PeopleRepository(peopleDao).also { _instance = it }
            }
    }

    /**
     * Retrieve all people, given a page number
     *
     * @param page the page number
     */
    suspend fun getPeople(page: Int) = peopleDao.getPeople(page)

    /**
     * Search for people, given a string and a result page number
     *
     * @param page the page number
     * @param searchString search string to match with name field
     */
    suspend fun searchPeople(page: Int, searchString: String) = peopleDao.searchPeople(page, searchString)

    // Here a quick example of how we might be caching some data.
    // We would have to think long and hard in terms of using memory caching, as well as local
    // database, let alone in a primitive way like I just did, but this examplifies how the
    // repository can juggle several data sources.
    // I figured planets are few, and used many times. I also thought I would store Strings, which
    // would have been better in terms of simple cache, but the architecture favors Planet object.
    private val peopleMap = mutableMapOf<String, People>()

    /**
     * Retrieve a People object, given its direct url
     *
     * @param peopleUrl Url to the specific People entry
     */
    suspend fun getPeopleByUrl(peopleUrl: String): People? {
        if (peopleMap[peopleUrl] == null) {
            peopleMap[peopleUrl] = peopleDao.getPeopleByUrl(peopleUrl)
        }
        return peopleMap[peopleUrl]
    }
}