package com.example.swapi.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.swapi.R
import com.example.swapi.data.model.Planet
import com.example.swapi.ui.common.PlanetNameAdapter.DataViewHolder

/**
 * Simple Planet Adapter for lists of planets
 *
 * TODO Create a main class for simple adapters. The reason I didn't do this yet is I would
 *  have to abstract the Swapi serialized objects to have a common "getName" function first.
 */
class PlanetNameAdapter(private val planetList: ArrayList<Planet>,
                        private val adapterActionProvider: AdapterActionProvider<Planet>)
    : RecyclerView.Adapter<DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(planet: Planet, adapterActionProvider: AdapterActionProvider<Planet>) {
            itemView.apply {
                val textView: TextView = itemView.findViewById(R.id.text_name)
                textView.text = planet.name

                itemView.setOnClickListener {
                    itemView.findNavController().navigate(adapterActionProvider.getAdapterAction(planet))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.planet_item_layout, parent, false))

    override fun getItemCount(): Int = planetList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(planetList[position], adapterActionProvider)
    }

    fun fillPlanets(planet: List<Planet>) {
        this.planetList.apply {
            clear()
            addAll(planet)
        }
    }

    fun addPlanet(planet: Planet) {
        this.planetList.apply {
            add(planet)
        }
    }
}