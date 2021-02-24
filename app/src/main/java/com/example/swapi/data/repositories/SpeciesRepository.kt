package com.example.swapi.data.repositories

import com.example.swapi.data.dao.SpeciesSwapiDao
import com.example.swapi.data.model.Species

/**
 * SpeciesRepository will have ownership over Species data
 *
 * It would be utilized to triage and balance between API calls, local database and/or other means
 * of caching we probably won't be implementing in this first version...
 *
 * It will also serve to centralize dependencies for testing purpose (we will see if I get the time
 * to do some of that in v1)
 */
class SpeciesRepository private constructor(private val speciesDao: SpeciesSwapiDao) {

    companion object {
        @Volatile private var _instance: SpeciesRepository? = null

        fun getInstance(speciesDao: SpeciesSwapiDao) =
                _instance ?: synchronized(this) {
                    _instance ?: SpeciesRepository(speciesDao).also { _instance = it }
                }
    }

    /**
     * Retrieve all species, given a page number
     *
     * @param page the page number
     */
    suspend fun getSpecies(page: Int) = speciesDao.getSpecies(page)

    /**
     * Search for species, given a string and a result page number
     *
     * @param page the page number
     * @param searchString search string to match with name field
     */
    suspend fun searchSpecies(page: Int, searchString: String) = speciesDao.searchSpecies(page, searchString)

    // Here a quick example of how we might be caching some data.
    // We would have to think long and hard in terms of using memory caching, as well as local
    // database, let alone in a primitive way like I just did, but this examplifies how the
    // repository can juggle several data sources.
    // I figured species are few, and used many times. I also thought I would store Strings, which
    // would have been better in terms of simple cache, but the architecture favors Species object.
    private val speciesMap = mutableMapOf<String, Species>()

    /**
     * Retrieve a Species object, given its direct url
     *
     * @param speciesUrl Url to the specific Species entry
     */
    suspend fun getSpeciesByUrl(speciesUrl: String): Species? {
        if (speciesMap[speciesUrl] == null) {
            speciesMap[speciesUrl] = speciesDao.getSpeciesByUrl(speciesUrl)
        }
        return speciesMap[speciesUrl]
    }
}