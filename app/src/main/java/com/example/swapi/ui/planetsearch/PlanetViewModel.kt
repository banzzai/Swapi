package com.example.swapi.ui.planetsearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.swapi.data.model.Planet
import com.example.swapi.data.model.Results
import com.example.swapi.data.repositories.PlanetRepository
import com.example.swapi.ui.peoplesearch.PeopleViewModel
import com.example.swapi.utils.retrofit.Resource
import kotlinx.coroutines.Dispatchers

/**
 * ViewModel for the Planet Fragment, containing paginated planet lists, from "All Planets" or searches.
 * @see PlanetFragment
 *
 * @arg planetRepository PlanetRepository responsible for access to planet lists
 */
class PlanetViewModel(private val planetRepository: PlanetRepository) : ViewModel() {
    private val TAG = PlanetViewModel::class.toString()

    companion object {
        const val FIRST_PAGE = 1
        const val CURRENT_PAGE = -1
        const val NEXT_PAGE = -2
        const val PREVIOUS_PAGE = -3
    }

    // Storing the last results for whenever it gets called for twice in a row
    private var lastResults: Results<Planet> = Results(0, "null", "null", listOf())
    // Storing latest page hit, to use with CURRENT_PAGE, NEXT_PAGE and PREVIOUS_PAGE
    private var page: Int = FIRST_PAGE
    // Storing the last search string, so that once a search was made, we can focus on page turns
    // and maintain the search.
    private var search: String? = null

    /**
     * Using Planet Repository to retrieve lists of Planet, given a page and a search String.
     * We also store the last search, so that getPlanetPage(CURRENT_PAGE, null) can immediately
     * return.
     *
     * @param pageTarget page to retrieve
     * @param searchString search string to match with the name field
     */
    fun getPlanetPage(pageTarget: Int, searchString: String?) = liveData(Dispatchers.IO) {
        var localPage = pageTarget
        when (pageTarget) {
            CURRENT_PAGE -> localPage = page
            PREVIOUS_PAGE -> localPage = if (page > 1) (page - 1) else page
            NEXT_PAGE -> localPage = page + 1
        }

        // We maintain a search string, so that list fragment can ask for new pages of the search
        // results without having to provide the search string again. It would be worth pondering
        // if that responsibility should be sitting here and be implemented this way.
        val localSearch = searchString ?: search

        emit(Resource.loading(data = null))
        try {
            // CURRENT_PAGE, no searchString or same searchString as previously, and there is cached
            // content. Let's return from the cache
            if (localPage == page && (searchString == null || searchString == search)
                    && lastResults.results.isNotEmpty()) {
                Log.d(TAG, "Loading from viewmodel cache")
                emit(Resource.success(data = lastResults))
            } else if (localSearch == null) {
                Log.d(TAG, "Network getPlanet call")
                lastResults = planetRepository.getPlanet(localPage)
                emit(Resource.success(data = lastResults))
            } else {
                Log.d(TAG, "Network searchPlanet call")
                lastResults = planetRepository.searchPlanet(localPage, localSearch)
                emit(Resource.success(data = lastResults))
            }
            page = localPage
            search = localSearch
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}