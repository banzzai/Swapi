package com.example.swapi.ui.species

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
import com.example.swapi.data.model.Species
import com.example.swapi.data.model.Planet
import com.example.swapi.ui.common.AdapterActionProvider
import com.example.swapi.ui.common.CommonFragment
import com.example.swapi.ui.common.PeopleNameAdapter
import com.example.swapi.utils.depencies.Injector
import com.example.swapi.data.livedata.SwapiLiveDataObserver as SwapiObserver

class SpeciesDetailsFragment: CommonFragment(), AdapterActionProvider<People> {
    private val TAG = SpeciesDetailsFragment::class.toString()
    private val args: SpeciesDetailsFragmentArgs by navArgs()
    private lateinit var detailsSpecies: Species

    // Ui elements
    private lateinit var viewModel: SpeciesDetailsViewModel
    private lateinit var homeworldText: TextView
    private lateinit var peopleText: TextView
    private lateinit var peopleRecycleView: RecyclerView
    private lateinit var adapter: PeopleNameAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_species_details, container, false)
        initView(root)

        getHomeworld()
        getPeople()

        return root
    }

    private fun initView(root: View) {
        val factory = Injector.injectSpeciesDetailsViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(SpeciesDetailsViewModel::class.java)

        // I am split between conserving the API "Species" and using something I deem more user
        // friendly. For UI, and clarity in this class, we will use "character"
        detailsSpecies = args.species

        // Fields filled with data from the arg object
        detailsSpecies.run {
            fillText(root, R.id.text_details_name, name)
            fillText(root, R.id.text_details_classification, classification)
            fillText(root, R.id.text_details_designation, designation)
            fillText(root, R.id.text_details_lifespan, averageLifespan)
            fillText(root, R.id.text_details_height, averageHeight)
            fillText(root, R.id.text_details_language, language)
            fillText(root, R.id.text_details_skin_color, skinColor)
            fillText(root, R.id.text_details_hair_color, hairColor)
            fillText(root, R.id.text_details_eye_color, eyeColor)
        }

        // Fields needing additional live data update to be filled
        homeworldText = root.findViewById<View>(R.id.text_details_homeworld) as TextView
        peopleText = root.findViewById<View>(R.id.text_labelfor_people) as TextView

        // Recycle view for the list of starships
        peopleRecycleView = root.findViewById<View>(R.id.recycler_view_people) as RecyclerView
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
                            val action = SpeciesDetailsFragmentDirections.actionNavSpeciesDetailsToNavPlanetDetail(data)
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

        detailsSpecies.homeworld?.let { world -> viewModel.getPlanetByUrl(world).observe(
            viewLifecycleOwner, planetObserver)
        }
    }

    private fun getPeople() {
        val peopleObserver = object : SwapiObserver<People>() {
            override fun onSuccess(data: People?) {
                peopleText.visibility = View.VISIBLE
                peopleRecycleView.visibility = View.VISIBLE

                data?.let { people ->
                    adapter.apply {
                        addPeople((people))
                        notifyDataSetChanged()
                    }
                }
            }
            override fun onError(errorMessage: String) {
                Toast.makeText(activity, getString(R.string.people_list_error),
                        Toast.LENGTH_LONG).show()
                Log.e(TAG, errorMessage)
            }
        }

        detailsSpecies.people?.forEach() { people -> viewModel.getPeopleByUrl(people).observe(
            viewLifecycleOwner, peopleObserver)
        }
    }

    // Seems like there would be a better solution than this, but I haven't had time to look up how
    // to make the same type of adapter cause different actions based on which fragment it is in.
    // It didn't feel right to pass a reference to the whole fragment.
    override fun getAdapterAction(item: People) = SpeciesDetailsFragmentDirections.actionNavSpeciesDetailsToNavPeopleDetail(item)
}