package com.example.swapi.ui.starship

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.swapi.data.repositories.PeopleRepository
import com.example.swapi.utils.retrofit.Resource
import kotlinx.coroutines.Dispatchers

/**
 * ViewModel for the StarshipDetailsViewModel, displaying details about a starship
 * @see StarshipDetailsFragment
 *
 * @arg peopleRepository PeopleRepository responsible for access to people information
 */
class StarshipDetailsViewModel(private val peopleRepository: PeopleRepository) : ViewModel() {

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
}