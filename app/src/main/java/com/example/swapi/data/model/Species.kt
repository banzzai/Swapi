package com.example.swapi.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Species object
 *
 * From https://swapi.dev/documentation#species
 *
 * name string -- The name of this species.
 * classification string -- The classification of this species, such as "mammal" or "reptile".
 * designation string -- The designation of this species, such as "sentient".
 * average_height string -- The average height of this species in centimeters.
 * average_lifespan string -- The average lifespan of this species in years.
 * eye_colors string -- A comma-separated string of common eye colors for this species, "none" if this species does not typically have eyes.
 * hair_colors string -- A comma-separated string of common hair colors for this species, "none" if this species does not typically have hair.
 * skin_colors string -- A comma-separated string of common skin colors for this species, "none" if this species does not typically have skin.
 * language string -- The language commonly spoken by this species.
 * homeworld string -- The URL of a planet resource, a planet that this species originates from.
 * people array -- An array of People URL Resources that are a part of this species.
 * films array -- An array of Film URL Resources that this species has appeared in.
 * url string -- the hypermedia URL of this resource.
 * created string -- the ISO 8601 date format of the time that this resource was created.
 * edited string -- the ISO 8601 date format of the time that this resource was edited.
 */
@Parcelize
data class Species(
    val name: String,
    val classification: String,
    val designation: String,
    @SerializedName("average_height")
    val averageHeight: String,
    @SerializedName("average_lifespan")
    val averageLifespan: String,
    @SerializedName("eye_colors")
    val eyeColor: String,
    @SerializedName("hair_colors")
    val hairColor: String,
    @SerializedName("skin_colors")
    val skinColor: String,
    val language: String,
    val homeworld: String,
    val people: List<String>,
    val films: List<String>,
    val url: String,
    val created: String,
    val edited: String) : Parcelable
