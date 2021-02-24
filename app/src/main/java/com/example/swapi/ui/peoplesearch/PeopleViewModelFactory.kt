package com.example.swapi.ui.peoplesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swapi.data.repositories.PeopleRepository

/**
 * Factory for viewmodels displaying people lists
 * @see PeopleFragment
 */
class PeopleViewModelFactory(private val peopleRepository: PeopleRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PeopleViewModel(peopleRepository) as T
    }
}