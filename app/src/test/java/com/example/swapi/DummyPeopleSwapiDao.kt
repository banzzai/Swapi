package com.example.swapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.swapi.data.model.People
import com.example.swapi.utils.retrofit.ApiService

/**
 * DummyPeopleSwapiDao
 * This DAO should be used to unit test getting data from a People DAO. I haven't furthered those
 * tests, this is what I already have from my work on the overall architecture.
 */
class DummyPeopleSwapiDao() {
    private val peopleList = mutableListOf<People>()

    // LiveData to be observed
    private val people = MutableLiveData<List<People>>()

    init {
        peopleList.add(People(
            "Luke Skywalker",
            "19 BBY",
            "Blue",
            "Male",
            "Fair",
            "172",
            "77",
            "Fair",
            "https://swapi.dev/api/planets/1/",
            listOf("LIST"),
            listOf("LIST"),
            listOf("LIST"),
            listOf("LIST"),
            "https://swapi.dev/api/people/1/",
            "2014-12-09T13:50:51.644000Z",
            "2014-12-10T13:52:43.172000Z"))
        // link the value list to the observable one
        people.value = peopleList
    }

    fun getPeople() = people as LiveData<List<People>>
}