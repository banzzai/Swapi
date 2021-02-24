package com.example.swapi.ui.common

import androidx.navigation.NavDirections

/**
 * This will be used so that fragments can register themselves to the adapters that are inside them
 * and provide them with the Android Navigation actions to use when the adapter items are clicked.
 */
interface AdapterActionProvider<T> {
    fun getAdapterAction(item: T): NavDirections
}
