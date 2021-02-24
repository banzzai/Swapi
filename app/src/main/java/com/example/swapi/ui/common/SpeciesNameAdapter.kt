package com.example.swapi.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.swapi.R
import com.example.swapi.data.model.Species
import com.example.swapi.ui.common.SpeciesNameAdapter.DataViewHolder

/**
 * Simple Species Adapter for lists of Species
 *
 * TODO Create a main class for simple adapters. The reason I didn't do this yet is I would
 *  have to abstract the Swapi serialized objects to have a common "getName" function first.
 */
class SpeciesNameAdapter(private val SpeciesList: ArrayList<Species>,
                         private val adapterActionProvider: AdapterActionProvider<Species>)
    : RecyclerView.Adapter<DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Species, adapterActionProvider: AdapterActionProvider<Species>) {
            itemView.apply {
                val textView: TextView = itemView.findViewById(R.id.text_name)
                textView.text = item.name

                itemView.setOnClickListener {
                    itemView.findNavController().navigate(adapterActionProvider.getAdapterAction(item))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.species_item_layout, parent, false))

    override fun getItemCount(): Int = SpeciesList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(SpeciesList[position], adapterActionProvider)
    }

    fun fillSpecies(Species: List<Species>) {
        this.SpeciesList.apply {
            clear()
            addAll(Species)
        }
    }

    fun addSpecies(Species: Species) {
        this.SpeciesList.apply {
            add(Species)
        }
    }
}