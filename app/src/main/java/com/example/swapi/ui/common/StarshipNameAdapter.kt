package com.example.swapi.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.swapi.R
import com.example.swapi.data.model.People
import com.example.swapi.data.model.Starship
import com.example.swapi.ui.common.StarshipNameAdapter.DataViewHolder

/**
 * Simple Starship Adapter for lists of starships
 *
 * TODO Create a main class for simple adapters. The reason I didn't do this yet is I would
 *  have to abstract the Swapi serialized objects to have a common "getName" function first.
 */
class StarshipNameAdapter(private val starshipList: ArrayList<Starship>,
                          private val adapterActionProvider: AdapterActionProvider<Starship>) : RecyclerView.Adapter<DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Starship, adapterActionProvider: AdapterActionProvider<Starship>) {
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
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.starship_item_layout, parent, false))

    override fun getItemCount(): Int = starshipList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(starshipList[position], adapterActionProvider)
    }

    fun addStarship(starships: Starship) {
        this.starshipList.apply {
            add(starships)
        }
    }
}