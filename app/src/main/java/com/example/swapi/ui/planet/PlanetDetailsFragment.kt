package com.example.swapi.ui.planet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swapi.R
import com.example.swapi.data.model.People
import com.example.swapi.data.model.Planet
import com.example.swapi.ui.common.AdapterActionProvider
import com.example.swapi.ui.common.CommonFragment
import com.example.swapi.ui.common.PeopleNameAdapter
import com.example.swapi.utils.depencies.Injector
import com.example.swapi.utils.retrofit.RequestStatus
import com.example.swapi.utils.retrofit.Resource

class PlanetDetailsFragment: CommonFragment(), AdapterActionProvider<People> {
    private val TAG = PlanetDetailsFragment::class.toString()
    private val args: PlanetDetailsFragmentArgs by navArgs()
    private lateinit var planet: Planet

    // Ui elements
    private lateinit var viewModel: PlanetDetailsViewModel
    private lateinit var peopleRecycleView: RecyclerView
    private lateinit var adapter: PeopleNameAdapter
    private lateinit var residents: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_planet_details, container, false)
        initView(root)

        getResidents()

        return root
    }

    private fun initView(root: View) {
        val factory = Injector.injectPlanetDetailsViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(PlanetDetailsViewModel::class.java)

        // I am split between conserving the API "people" and using something I deem more user
        // friendly. For UI, and clarity in this class, we will use "character"
        planet = args.planet

        // Fields filled with data from the arg object
        planet.run {
            fillText(root, R.id.text_details_name, name)
            fillText(root, R.id.text_details_population, population)
            fillText(root, R.id.text_details_rotation, rotationPeriod, "h")
            fillText(root, R.id.text_details_diameter, diameter, " Km")
            fillText(root, R.id.text_details_orbit, orbitalPeriod, " days")
            fillText(root, R.id.text_details_gravity, gravity)
            fillText(root, R.id.text_details_terrain, terrain)
            fillText(root, R.id.text_details_water, surfaceWater, "%")
            fillText(root, R.id.text_details_climate, climate)
        }

        // Recycle view for the list of residents
        residents = root.findViewById<View>(R.id.text_labelfor_residents) as TextView
        peopleRecycleView = root.findViewById<View>(R.id.recycler_view_residents) as RecyclerView
        peopleRecycleView.layoutManager = LinearLayoutManager(context)
        adapter = PeopleNameAdapter(arrayListOf(), this)
        peopleRecycleView.addItemDecoration(
            DividerItemDecoration(
                peopleRecycleView.context,
                (peopleRecycleView.layoutManager as LinearLayoutManager).orientation
            )
        )
        peopleRecycleView.adapter = adapter
    }

    private fun getResidents() {
        val residentObserver = Observer<Resource<People?>> { it ->
            it?.let { resource ->
                when (resource.status) {
                    RequestStatus.SUCCESS -> {
                        residents.visibility = View.VISIBLE
                        peopleRecycleView.visibility = View.VISIBLE

                        resource.data?.let { people ->
                            adapter.apply {
                                addPeople((people))
                                notifyDataSetChanged()
                            }
                        }
                    }
                    RequestStatus.ERROR -> {
                        Toast.makeText(
                            activity,
                            getString(R.string.details_species_error),
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e(TAG, it.message.toString())
                    }
                    RequestStatus.LOADING -> {
                        // Don't think we need anything in there for v1
                    }
                }
            }
        }

        planet.residents?.forEach() { people -> viewModel.getPeopleByUrl(people)
            .observe(viewLifecycleOwner, residentObserver)
        }
    }

    // Seems like there would be a better solution than this, but I haven't had time to look up how
    // to make the same type of adapter cause different actions based on which fragment it is in.
    // It didn't feel right to pass a reference to the whole fragment.
    override fun getAdapterAction(item: People) = PlanetDetailsFragmentDirections.actionNavPlanetDetailsToNavPeopleDetail(item)
}