package com.example.swapi.ui.species

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.swapi.data.repositories.PeopleRepository
import com.example.swapi.data.repositories.PlanetRepository
import com.example.swapi.utils.retrofit.Resource
import kotlinx.coroutines.Dispatchers

/**
 * ViewModel for the SpeciesDetailsViewModel, displaying details about a species
 * @see SpeciesDetailsFragment
 *
 * @arg peopleRepository PeopleRepository responsible for access to people information
 * @arg planetRepository PlanetRepository responsible for access to planet information
 */
class SpeciesDetailsViewModel(private val peopleRepository: PeopleRepository,
                              private val planetRepository: PlanetRepository) : ViewModel() {
    /**
     * Call on to the People repository to retrieve a People, by its url.
     *
     * @param peopleUrl Url to a specific People
     */
    fun getPeopleByUrl(peopleUrl: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = peopleRepository.getPeopleByUrl(peopleUrl)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    /**
     * Call on to the Planet repository to retrieve a Planet, by its url.
     *
     * @param planetUrl Url to a specific Planet
     */
    fun getPlanetByUrl(planetUrl: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = planetRepository.getPlanetByUrl(planetUrl)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}