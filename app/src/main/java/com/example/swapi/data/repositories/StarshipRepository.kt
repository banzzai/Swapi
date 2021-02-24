package com.example.swapi.data.repositories

import com.example.swapi.data.dao.StarshipSwapiDao
import com.example.swapi.data.model.Starship

/**
 * StarshipRepository will have ownership over Starship data
 *
 * It would be utilized to triage and balance between API calls, local database and/or other means
 * of caching we probably won't be implementing in this first version...
 *
 * It will also serve to centralize dependencies for testing purpose (we will see if I get the time
 * to do some of that in v1)
 */
class StarshipRepository private constructor(private val starshipDao: StarshipSwapiDao) {

    companion object {
        @Volatile private var _instance: StarshipRepository? = null

        fun getInstance(starshipDao: StarshipSwapiDao) =
                _instance ?: synchronized(this) {
                    _instance ?: StarshipRepository(starshipDao).also { _instance = it }
                }
    }

    // Here a quick example of how we might be caching some data.
    // We would have to think long and hard in terms of using memory caching, as well as local
    // database, let alone in a primitive way like I just did, but this examplifies how the
    // repository can juggle several data sources.
    // I figured starships are few, and used many times. I also thought I would store Strings, which
    // would have been better in terms of simple cache, but the architecture favors Starship object.
    private val starshipMap = mutableMapOf<String, Starship>()

    /**
     * Retrieve a Starship object, given its direct url
     *
     * @param starshipUrl Url to the specific Starship entry
     */
    suspend fun getStarshipByUrl(starshipUrl: String): Starship? {
        if (starshipMap[starshipUrl] == null) {
            starshipMap[starshipUrl] = starshipDao.getStarshipByUrl(starshipUrl)
        }
        return starshipMap[starshipUrl]
    }
}