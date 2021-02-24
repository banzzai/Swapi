package com.example.swapi.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.swapi.R
import com.example.swapi.data.model.People
import com.example.swapi.ui.common.PeopleNameAdapter.DataViewHolder

/**
 * Simple People Adapter for lists of people
 *
 * TODO Create a main class for simple adapters. The reason I didn't do this yet is I would
 *  have to abstract the Swapi serialized objects to have a common "getName" function first.
 */
class PeopleNameAdapter(private val peopleList: ArrayList<People>,
                        private val adapterActionProvider: AdapterActionProvider<People>)
    : RecyclerView.Adapter<DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: People, adapterActionProvider: AdapterActionProvider<People>) {
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
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.people_item_layout, parent, false))

    override fun getItemCount(): Int = peopleList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(peopleList[position], adapterActionProvider)
    }

    fun fillPeople(people: List<People>) {
        this.peopleList.apply {
            clear()
            addAll(people)
        }
    }

    fun addPeople(people: People) {
        this.peopleList.apply {
            add(people)
        }
    }
}