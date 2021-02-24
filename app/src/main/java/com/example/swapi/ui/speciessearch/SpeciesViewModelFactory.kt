package com.example.swapi.ui.speciessearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swapi.data.repositories.SpeciesRepository

/**
 * Factory for viewmodels displaying species lists
 * @see SpeciesFragment
 */
class SpeciesViewModelFactory(private val speciesRepository: SpeciesRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SpeciesViewModel(speciesRepository) as T
    }
}