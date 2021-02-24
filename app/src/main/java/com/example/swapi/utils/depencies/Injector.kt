package com.example.swapi.utils.depencies

import com.example.swapi.utils.retrofit.SwapiRetrofitHelper
import com.example.swapi.data.repositories.PeopleRepository
import com.example.swapi.data.repositories.PlanetRepository
import com.example.swapi.data.repositories.SpeciesRepository
import com.example.swapi.data.repositories.StarshipRepository
import com.example.swapi.ui.people.PeopleDetailsViewModelFactory
import com.example.swapi.ui.peoplesearch.PeopleViewModelFactory
import com.example.swapi.ui.planet.PlanetDetailsViewModelFactory
import com.example.swapi.ui.planetsearch.PlanetViewModelFactory
import com.example.swapi.ui.species.SpeciesDetailsViewModelFactory
import com.example.swapi.ui.speciessearch.SpeciesViewModelFactory
import com.example.swapi.ui.starship.StarshipDetailsViewModelFactory

/**
 * Injector will be used to inject dependencies, mostly into factories, in order to centralize and
 * facilitate testing (in the future if not v1)
 */
object Injector {
    /**
     * Injecting a PeopleViewModelFactory into a fragment for lists of people
     * @See PeopleFragment
     */
    fun injectPeopleViewModelFactory(): PeopleViewModelFactory {
        // Getting our factory, repo, API helper and DAO all in one place.
        val peopleRepository = PeopleRepository.getInstance(SwapiRetrofitHelper.getInstance().peopleDao)
        return PeopleViewModelFactory(peopleRepository)
    }

    /**
     * Injecting a PlanetViewModelFactory into a fragment for lists of planets
     * @See PlanetFragment
     */
    fun injectPlanetViewModelFactory(): PlanetViewModelFactory {
        // Getting our factory, repo, API helper and DAO all in one place.
        val planetRepository = PlanetRepository.getInstance(SwapiRetrofitHelper.getInstance().planetDao)
        return PlanetViewModelFactory(planetRepository)
    }

    /**
     * Injecting a SpeciesViewModelFactory into a fragment for lists of species
     * @See SpeciesFragment
     */
    fun injectSpeciesViewModelFactory(): SpeciesViewModelFactory {
        // Getting our factory, repo, API helper and DAO all in one place.
        val speciesRepository = SpeciesRepository.getInstance(SwapiRetrofitHelper.getInstance().speciesDao)
        return SpeciesViewModelFactory(speciesRepository)
    }

    /**
     * Injecting a PeopleDetailsViewModelFactory into a fragment for details of people
     * @See PeopleDetailsFragment
     */
    fun injectPeopleDetailsViewModelFactory():
            PeopleDetailsViewModelFactory {
        // Getting our factory, repo, API helper and DAO all in one place.
        val speciesRepository = SpeciesRepository.getInstance(SwapiRetrofitHelper.getInstance().speciesDao)
        val planetRepository = PlanetRepository.getInstance(SwapiRetrofitHelper.getInstance().planetDao)
        val starshipRepository = StarshipRepository.getInstance(SwapiRetrofitHelper.getInstance().starshipSwapiDao)

        return PeopleDetailsViewModelFactory(speciesRepository, planetRepository, starshipRepository)
    }

    /**
     * Injecting a PlanetDetailsViewModelFactory into a fragment for details of planets
     * @See PlanetDetailsFragment
     */
    fun injectPlanetDetailsViewModelFactory(): PlanetDetailsViewModelFactory {
        // Getting our factory, repo, API helper and DAO all in one place.
        val peopleRepository = PeopleRepository.getInstance(SwapiRetrofitHelper.getInstance().peopleDao)

        return PlanetDetailsViewModelFactory(peopleRepository)
    }

    /**
     * Injecting a SpeciesDetailsViewModelFactory into a fragment for details of species
     * @See SpeciesDetailsFragment
     */
    fun injectSpeciesDetailsViewModelFactory(): SpeciesDetailsViewModelFactory {
        // Getting our factory, repo, API helper and DAO all in one place.
        val peopleRepository = PeopleRepository.getInstance(SwapiRetrofitHelper.getInstance().peopleDao)
        val planetRepository = PlanetRepository.getInstance(SwapiRetrofitHelper.getInstance().planetDao)

        return SpeciesDetailsViewModelFactory(peopleRepository, planetRepository)
    }

    /**
     * Injecting a StarshipDetailsViewModelFactory into a fragment for details of starships
     * @See StarshipDetailsFragment
     */
    fun injectStarshipDetailsViewModelFactory(): StarshipDetailsViewModelFactory {
        // Getting our factory, repo, API helper and DAO all in one place.
        val peopleRepository = PeopleRepository.getInstance(SwapiRetrofitHelper.getInstance().peopleDao)

        return StarshipDetailsViewModelFactory(peopleRepository)
    }
}