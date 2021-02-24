package com.example.swapi.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.swapi.data.repositories.PlanetRepository
import com.example.swapi.data.repositories.SpeciesRepository
import com.example.swapi.data.repositories.StarshipRepository
import com.example.swapi.utils.retrofit.Resource
import kotlinx.coroutines.Dispatchers

/**
 * ViewModel for the PeopleDetailsFragment, displaying details about a character
 * @see PeopleDetailsFragment
 *
 * @arg speciesRepository SpeciesRepository responsible for access to species information
 * @arg planetRepository PlanetRepository responsible for access to planet information
 * @arg starshipRepository StarshipRepository responsible for access to starship information
 */
class PeopleDetailsViewModel(private val speciesRepository: SpeciesRepository,
                             private val planetRepository: PlanetRepository,
                             private val starshipRepository: StarshipRepository) : ViewModel() {

    /**
     * Call on to the Species repository to retrieve a Species, by its url.
     *
     * @param speciesUrl Url to a specific Species
     */
    fun getSpeciesByUrl(speciesUrl: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = speciesRepository.getSpeciesByUrl(speciesUrl)))
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

    /**
     * Call on to the Starship repository to retrieve a Starship, by its url.
     *
     * @param starshipUrl Url to a specific Starship
     */
    fun getStarshipByUrl(starshipUrl: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = starshipRepository.getStarshipByUrl(starshipUrl)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}