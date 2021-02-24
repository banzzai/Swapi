package com.example.swapi.utils.retrofit

import com.example.swapi.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    //PEOPLE REQUESTS
    @GET("people")
    suspend fun getPeople(@Query("page") page: Int): Results<People>

    @GET("people")
    suspend fun getPeopleSearchPage(@Query("search") search: String, @Query("page") page: Int): Results<People>

    /**
     * The Schema provides us with full url for people, so instead of adding bug-prone parsing
     * to retrieve an ID, we will use just that.
     */
    @GET()
    suspend fun getPeopleByUrl(@Url peopleUrl: String): People

    //PLANETS REQUESTS
    @GET("planets")
    suspend fun getPlanet(@Query("page") page: Int): Results<Planet>

    @GET("planets")
    suspend fun getPlanetSearchPage(@Query("search") search: String, @Query("page") page: Int): Results<Planet>

    /**
     * The Schema provides us with full url for planets, so instead of adding bug-prone parsing
     * to retrieve an ID, we will use just that.
     */
    @GET()
    suspend fun getPlanetByUrl(@Url planetUrl: String): Planet

    //SPECIES REQUESTS
    @GET("species")
    suspend fun getSpecies(@Query("page") page: Int): Results<Species>

    @GET("species")
    suspend fun getSpeciesSearchPage(@Query("search") search: String, @Query("page") page: Int): Results<Species>

    /**
     * The Schema provides us with full url for species, so instead of adding bug-prone parsing
     * to retrieve an ID, we will use just that.
     */
    @GET()
    suspend fun getSpeciesByUrl(@Url speciesUrl: String): Species

    //STARSHIP REQUESTS
    /**
     * The Schema provides us with full url for starships, so instead of adding bug-prone parsing
     * to retrieve an ID, we will use just that.
     */
    @GET()
    suspend fun getStarshipByUrl(@Url starshipUrl: String): Starship
}