package com.example.swapi.ui.starship

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swapi.data.repositories.PeopleRepository

/**
 * Factory for viewmodels displaying starship details
 * @see StarshipDetailsFragment
 *
 * @arg peopleRepository PeopleRepository responsible for access to pilots information
 */
class StarshipDetailsViewModelFactory(private val peopleRepository: PeopleRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StarshipDetailsViewModel(peopleRepository) as T
    }
}