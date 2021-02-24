package com.example.swapi.ui.species

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swapi.data.repositories.PeopleRepository
import com.example.swapi.data.repositories.PlanetRepository

/**
 * Factory for viewmodels displaying species details
 * @see SpeciesDetailsFragment
 *
 * @arg peopleRepository PeopleRepository responsible for access to species information
 * @arg planetRepository PlanetRepository responsible for access to planet information
 */
class SpeciesDetailsViewModelFactory(private val peopleRepository: PeopleRepository, private val planetRepository: PlanetRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SpeciesDetailsViewModel(peopleRepository, planetRepository) as T
    }
}