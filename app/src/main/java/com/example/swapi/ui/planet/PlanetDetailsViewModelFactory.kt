package com.example.swapi.ui.planet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swapi.data.repositories.PeopleRepository

/**
 * Factory for viewmodels displaying planet details
 * @see PlanetDetailsFragment
 *
 * @arg peopleRepository PeopleRepository responsible for access to species information
 */
class PlanetDetailsViewModelFactory(private val peopleRepository: PeopleRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlanetDetailsViewModel(peopleRepository) as T
    }
}