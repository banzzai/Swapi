package com.example.swapi.data.repositories

import com.example.swapi.data.dao.PlanetSwapiDao
import com.example.swapi.data.model.Planet

/**
 * PlanetRepository will have ownership over Planet data
 *
 * It would be utilized to triage and balance between API calls, local database and/or other means
 * of caching we probably won't be implementing in this first version...
 *
 * It will also serve to centralize dependencies for testing purpose (we will see if I get the time
 * to do some of that in v1)
 */
class PlanetRepository private constructor(private val planetDao: PlanetSwapiDao) {

    companion object {
        @Volatile private var _instance: PlanetRepository? = null

        fun getInstance(planetDao: PlanetSwapiDao) =
            _instance ?: synchronized(this) {
                _instance ?: PlanetRepository(planetDao).also { _instance = it }
            }
    }

    /**
     * Retrieve all planets, given a page number
     *
     * @param page the page number
     */
    suspend fun getPlanet(page: Int) = planetDao.getPlanet(page)

    /**
     * Search for a planet, given a string and a result page number
     *
     * @param page the page number
     * @param searchString search string to match with name field
     */
    suspend fun searchPlanet(page: Int, search: String) = planetDao.searchPlanet(page, search)

    // Here a quick example of how we might be caching some data.
    // We would have to think long and hard in terms of using memory caching, as well as local
    // database, let alone in a primitive way like I just did, but this examplifies how the
    // repository can juggle several data sources.
    // I figured planets are few, and used many times. I also thought I would store Strings, which
    // would have been better in terms of simple cache, but the architecture favors Planet object.
    private val planetMap = mutableMapOf<String, Planet>()

    /**
     * Retrieve a Planet object, given its direct url
     *
     * @param planetUrl Url to the specific Planet entry
     */
    suspend fun getPlanetByUrl(planetUrl: String): Planet? {
        if (planetMap[planetUrl] == null) {
            planetMap[planetUrl] = planetDao.getPlanetByUrl(planetUrl)
        }
        return planetMap[planetUrl]
    }
}