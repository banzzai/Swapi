package com.example.swapi.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swapi.data.repositories.PlanetRepository
import com.example.swapi.data.repositories.SpeciesRepository
import com.example.swapi.data.repositories.StarshipRepository

/**
 * Factory for viewmodels displaying people details
 * @see PeopleDetailsFragment
 *
 * @arg speciesRepository SpeciesRepository responsible for access to species information
 * @arg planetRepository PlanetRepository responsible for access to planet information
 * @arg starshipRepository StarshipRepository responsible for access to starship information
 */
class PeopleDetailsViewModelFactory(private val speciesRepository: SpeciesRepository,
                                    private val planetRepository: PlanetRepository,
                                    private val starshipRepository: StarshipRepository
)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PeopleDetailsViewModel(speciesRepository, planetRepository, starshipRepository) as T
    }
}