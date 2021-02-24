package com.example.swapi.ui.starship

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swapi.R
import com.example.swapi.data.livedata.SwapiLiveDataObserver
import com.example.swapi.data.model.People
import com.example.swapi.data.model.Starship
import com.example.swapi.ui.common.AdapterActionProvider
import com.example.swapi.ui.common.CommonFragment
import com.example.swapi.ui.common.PeopleNameAdapter
import com.example.swapi.utils.depencies.Injector

class StarshipDetailsFragment: CommonFragment(), AdapterActionProvider<People> {
    private val TAG = StarshipDetailsFragment::class.toString()
    private val args: StarshipDetailsFragmentArgs by navArgs()
    private lateinit var starship: Starship

    // Ui elements
    private lateinit var viewModel: StarshipDetailsViewModel
    private lateinit var pilotsRecycleView: RecyclerView
    private lateinit var adapter: PeopleNameAdapter
    private lateinit var pilotsLabel: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_starship_details, container, false)
        initView(root)

        getPilots()

        return root
    }

    private fun initView(root: View) {
        val factory = Injector.injectStarshipDetailsViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(StarshipDetailsViewModel::class.java)

        starship = args.starship

        // Fields filled with data from the arg object
        starship.run {
            fillText(root, R.id.text_details_name, name)
            fillText(root, R.id.text_details_model, model)
            fillText(root, R.id.text_details_manufacturer, manufacturer)
            fillText(root, R.id.text_details_class, starshipClass)
            fillText(root, R.id.text_details_crew, crew)
            fillText(root, R.id.text_details_passengers, passengers)
            fillText(root, R.id.text_details_hyperdrive, hyperdriveRating)
            fillText(root, R.id.text_details_mglt, MGLT)
            fillText(root, R.id.text_details_consumables, consumables)
            fillText(root, R.id.text_details_length, length, "m")
            fillText(root, R.id.text_details_cost, costInCredits, " credits")
            fillText(root, R.id.text_details_cargo, cargoCapacity, " Kg")
            fillText(root, R.id.text_details_speed, maxAtmospheringSpeed)
        }

        // Recycle view for the list of residents
        pilotsLabel = root.findViewById<View>(R.id.text_labelfor_pilots) as TextView
        pilotsRecycleView = root.findViewById<View>(R.id.recycler_view_pilots) as RecyclerView
        pilotsRecycleView.layoutManager = LinearLayoutManager(context)
        adapter = PeopleNameAdapter(arrayListOf(), this)
        pilotsRecycleView.addItemDecoration(
            DividerItemDecoration(
                pilotsRecycleView.context,
                (pilotsRecycleView.layoutManager as LinearLayoutManager).orientation
            )
        )
        pilotsRecycleView.adapter = adapter
    }

    private fun getPilots() {
        val residentObserver = object : SwapiLiveDataObserver<People>() {
            override fun onSuccess(data: People?) {
                pilotsLabel.visibility = View.VISIBLE
                pilotsRecycleView.visibility = View.VISIBLE

                data?.let { people ->
                    adapter.apply {
                        addPeople((people))
                        notifyDataSetChanged()
                    }
                }
            }
            override fun onError(errorMessage: String) {
                Toast.makeText( activity, getString(R.string.details_species_error),
                        Toast.LENGTH_LONG ).show()
                Log.e(TAG, errorMessage)
            }
        }

        starship.pilots?.forEach() { people -> viewModel.getPeopleByUrl(people)
            .observe(viewLifecycleOwner, residentObserver)
        }
    }

    // Seems like there would be a better solution than this, but I haven't had time to look up how
    // to make the same type of adapter cause different actions based on which fragment it is in.
    // It didn't feel right to pass a reference to the whole fragment.
    override fun getAdapterAction(item: People) = StarshipDetailsFragmentDirections.actionNavStarshipDetailsToNavPeopleDetail(item)
}