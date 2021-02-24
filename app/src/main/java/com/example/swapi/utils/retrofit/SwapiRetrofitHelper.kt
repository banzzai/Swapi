package com.example.swapi.utils.retrofit

import com.example.swapi.data.dao.PeopleSwapiDao
import com.example.swapi.data.dao.PlanetSwapiDao
import com.example.swapi.data.dao.SpeciesSwapiDao
import com.example.swapi.data.dao.StarshipSwapiDao
import com.example.swapi.utils.retrofit.SwapiRetrofitBuilder.apiService

/**
 * Swapi Helper will serve as the central repository for Swapi data.
 * We will keep all the DAO and mirror a database implementation this way.
 *
 * @param apiService retrofit API service, to make the requests.
 */
class SwapiRetrofitHelper private constructor(apiService: ApiService) {

    var peopleDao = PeopleSwapiDao(apiService)
    var speciesDao = SpeciesSwapiDao(apiService)
    var planetDao = PlanetSwapiDao(apiService)
    var starshipSwapiDao = StarshipSwapiDao(apiService)
        private set

    companion object {
        @Volatile private var _instance: SwapiRetrofitHelper? = null

        fun getInstance() =
            _instance ?: synchronized(this) {
                _instance ?: SwapiRetrofitHelper(apiService).also { _instance = it }
            }
    }
}