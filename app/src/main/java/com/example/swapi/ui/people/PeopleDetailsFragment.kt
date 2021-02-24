package com.example.swapi.ui.people

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swapi.R
import com.example.swapi.data.model.People
import com.example.swapi.data.model.Planet
import com.example.swapi.data.model.Species
import com.example.swapi.data.model.Starship
import com.example.swapi.ui.common.AdapterActionProvider
import com.example.swapi.ui.common.CommonFragment
import com.example.swapi.ui.common.StarshipNameAdapter
import com.example.swapi.utils.depencies.Injector
import com.example.swapi.data.livedata.SwapiLiveDataObserver as SwapiObserver

class PeopleDetailsFragment: CommonFragment(), AdapterActionProvider<Starship> {
    private val TAG = PeopleDetailsFragment::class.toString()
    private val args: PeopleDetailsFragmentArgs by navArgs()
    private lateinit var character: People

    // Ui elements
    private lateinit var viewModel: PeopleDetailsViewModel
    private lateinit var speciesText: TextView
    private lateinit var homeworldText: TextView
    private lateinit var starshipText: TextView
    private lateinit var starshipRecycleView: RecyclerView
    private lateinit var adapter: StarshipNameAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_people_details, container, false)
        initView(root)

        getSpecies()
        getHomeworld()
        getStarships()

        return root
    }

    private fun initView(root: View) {
        val factory = Injector.injectPeopleDetailsViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(PeopleDetailsViewModel::class.java)

        // I am split between conserving the API "people" and using something I deem more user
        // friendly. For UI, and clarity in this class, we will use "character"
        character = args.people

        // Fields filled with data from the arg object
        character.run {
            fillText(root, R.id.text_details_name, name)
            fillText(root, R.id.text_details_birth_year, birthYear)
            fillText(root, R.id.text_details_gender, gender)
            fillText(root, R.id.text_details_skin_color, skinColor)
            fillText(root, R.id.text_details_eye_color, eyeColor)
            fillText(root, R.id.text_details_hair_color, hairColor)
            fillText(root, R.id.text_details_height, height)
            fillText(root, R.id.text_details_mass, mass)
        }

        // Fields needing additional live data update to be filled
        speciesText = root.findViewById<View>(R.id.text_details_species) as TextView
        homeworldText = root.findViewById<View>(R.id.text_details_homeworld) as TextView
        starshipText = root.findViewById<View>(R.id.text_labelfor_starships) as TextView

        // Recycle view for the list of starships
        starshipRecycleView = root.findViewById<View>(R.id.recycler_view_starships) as RecyclerView
        starshipRecycleView.layoutManager = LinearLayoutManager(context)
        adapter = StarshipNameAdapter(arrayListOf(), this)
        starshipRecycleView.addItemDecoration(
            DividerItemDecoration(
                starshipRecycleView.context,
                (starshipRecycleView.layoutManager as LinearLayoutManager).orientation
            )
        )
        starshipRecycleView.adapter = adapter
    }

    private fun getSpecies() {
        val speciesObserver = object : SwapiObserver<Species>() {
            override fun onSuccess(data: Species?) {
                data?.name?.let { speciesName ->
                    //TODO I am assuming that people having several species assigned to them is more
                    // of a bug than a feature, but it would be good to check. For now I will not
                    // support it, and instead display the first/last entry in the species list, so
                    // that I can make that into a clickable link.
                    context?.let { context ->
                        val content = SpannableString(speciesName)
                        content.setSpan(UnderlineSpan(), 0, content.length, 0)
                        speciesText.text = content
                        speciesText.isClickable = true
                        speciesText.setTextColor(getColor(context, R.color.teal_700))

                        speciesText.setOnClickListener {
                            val action = PeopleDetailsFragmentDirections.actionNavPeopleDetailsToNavSpeciesDetail(data)
                            it.findNavController().navigate(action)
                        }
                    }
                }
            }
            override fun onError(errorMessage: String) {
                Toast.makeText(
                        activity,
                        getString(R.string.details_species_error),
                        Toast.LENGTH_LONG
                ).show()
                Log.e(TAG, errorMessage)
            }
        }

        // Human is the default value for species, because of a bug in Swapi that doesn't
        // assign a species to humans
        if (character.species.isEmpty()) {
            speciesText.text = getString(R.string.people_details_species_Human)
        }

        character.species?.forEach() { species -> viewModel.getSpeciesByUrl(species).observe(
            viewLifecycleOwner, speciesObserver)
        }
    }

    private fun getHomeworld() {
        val planetObserver = object : SwapiObserver<Planet>() {
            override fun onSuccess(data: Planet?) {
                data?.name?.let { planetName ->
                    context?.let { context ->
                        val content = SpannableString(planetName)
                        content.setSpan(UnderlineSpan(), 0, content.length, 0)
                        homeworldText.text = content
                        homeworldText.isClickable = true
                        homeworldText.setTextColor(getColor(context, R.color.teal_700))

                        homeworldText.setOnClickListener {
                            val action = PeopleDetailsFragmentDirections.actionNavPeopleDetailsToNavPlanetDetail(data)
                            it.findNavController().navigate(action)
                        }
                    }
                }
            }
            override fun onError(errorMessage: String) {
                Toast.makeText(activity, getString(R.string.details_homeworld_error),
                        Toast.LENGTH_LONG).show()
                Log.e(TAG, errorMessage)
            }
        }

        character.homeworld?.let { world -> viewModel.getPlanetByUrl(world).observe(
            viewLifecycleOwner, planetObserver)
        }
    }

    private fun getStarships() {
        val planetObserver = object : SwapiObserver<Starship>() {
            override fun onSuccess(data: Starship?) {
                starshipText.visibility = View.VISIBLE
                starshipRecycleView.visibility = View.VISIBLE

                data?.let { starship ->
                    adapter.apply {
                        addStarship((starship))
                        notifyDataSetChanged()
                    }
                }
            }
            override fun onError(errorMessage: String) {
                Toast.makeText(activity, getString(R.string.details_species_error),
                        Toast.LENGTH_LONG).show()
                Log.e(TAG, errorMessage)
            }
        }

        character.starships?.forEach() { starship -> viewModel.getStarshipByUrl(starship).observe(
            viewLifecycleOwner, planetObserver)
        }
    }

    // Seems like there would be a better solution than this, but I haven't had time to look up how
    // to make the same type of adapter cause different actions based on which fragment it is in.
    // It didn't feel right to pass a reference to the whole fragment.
    override fun getAdapterAction(item: Starship) = PeopleDetailsFragmentDirections.actionNavPeopleDetailsToNavStarshipDetail(item)
}