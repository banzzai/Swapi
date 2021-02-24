package com.example.swapi.ui.planetsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swapi.data.repositories.PlanetRepository

/**
 * Factory for viewmodels displaying planet lists
 * @see PlanetFragment
 */
class PlanetViewModelFactory(private val peopleRepository: PlanetRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlanetViewModel(peopleRepository) as T
    }
}