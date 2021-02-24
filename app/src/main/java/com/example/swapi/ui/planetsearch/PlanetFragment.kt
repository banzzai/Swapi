package com.example.swapi.ui.planetsearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swapi.R
import com.example.swapi.data.livedata.SwapiLiveDataObserver
import com.example.swapi.data.model.Planet
import com.example.swapi.data.model.Results
import com.example.swapi.ui.common.AdapterActionProvider
import com.example.swapi.ui.common.CommonFragment
import com.example.swapi.ui.common.PlanetNameAdapter
import com.example.swapi.ui.planetsearch.PlanetViewModel.Companion.FIRST_PAGE
import com.example.swapi.ui.planetsearch.PlanetViewModel.Companion.CURRENT_PAGE
import com.example.swapi.ui.planetsearch.PlanetViewModel.Companion.NEXT_PAGE
import com.example.swapi.ui.planetsearch.PlanetViewModel.Companion.PREVIOUS_PAGE
import com.example.swapi.utils.depencies.Injector
import com.example.swapi.utils.retrofit.RequestStatus.*

/**
 * PlanetFragment
 * Displays a list of planets and allows for searh and pagination.
 */
class PlanetFragment : CommonFragment(), AdapterActionProvider<Planet> {
    private val TAG = PlanetFragment::class.toString()

    // Ui elements
    private lateinit var viewModel: PlanetViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlanetNameAdapter
    private lateinit var listTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_planet_list, container, false)
        initView(root)

        // Getting current page from viewModel and asking for display
        getPlanet(CURRENT_PAGE)

        return root
    }

    private fun initView(root: View) {
        previousButton = root.findViewById(R.id.button_previous)
        previousButton.setOnClickListener {
            getPlanet(PREVIOUS_PAGE)
        }
        nextButton = root.findViewById(R.id.button_next)
        nextButton.setOnClickListener {
            getPlanet(NEXT_PAGE)
        }

        recyclerView = root.findViewById<View>(R.id.recycler_view) as RecyclerView
        progressBar = root.findViewById<View>(R.id.progress_bar) as ProgressBar

        val searchButton = root.findViewById<View>(R.id.button_search) as Button
        val searchText = root.findViewById<View>(R.id.edittext_search) as EditText
        searchButton.setOnClickListener { doSearch(searchText) }
        searchText.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    doSearch(searchText)
                    false
                }
                else -> false
            }
        }

        val factory = Injector.injectPlanetViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(PlanetViewModel::class.java)

        listTitle = root.findViewById<View>(R.id.text_list_title) as TextView

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PlanetNameAdapter(arrayListOf(), this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun doSearch(searchText: EditText) {
        getPlanet(FIRST_PAGE, searchText.text.toString())
    }

    private fun getPlanet(page: Int) {
        getPlanet(page, null)
    }

    private fun getPlanet(page: Int, searchString: String?) {
        val planetObserver = object : SwapiLiveDataObserver<Results<Planet>>() {
            override fun onSuccess(data: Results<Planet>?) {
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                data?.let { response ->
                    displayPlanetList(response)
                    listTitle.text = if (searchString != null) "Results for '$searchString'" else "All Planets"
                }
            }
            override fun onError(errorMessage: String) {
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                Toast.makeText(activity, getString(R.string.planet_list_error), Toast.LENGTH_LONG).show()
                Log.e(TAG, errorMessage)
            }
            override fun onLoading() {
                super.onLoading()
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        }

        viewModel.getPlanetPage(page, searchString).observe(viewLifecycleOwner, planetObserver)
    }

    private fun displayPlanetList(response: Results<Planet>) {
        //TODO Need to master this area. I didn't think retrofit was going to serialize nulls for
        // previous and next. Based on what elements/mechanisms is it doing so?
        previousButton.visibility = if (response.previous == null) View.INVISIBLE else View.VISIBLE
        nextButton.visibility = if (response.next == null) View.INVISIBLE else View.VISIBLE

        adapter.apply {
            fillPlanets(response.results)
            notifyDataSetChanged()
        }
    }

    // Seems like there would be a better solution than this, but I haven't had time to look up how
    // to make the same type of adapter cause different actions based on which fragment it is in.
    // It didn't feel right to pass a reference to the whole fragment.
    override fun getAdapterAction(item: Planet) = PlanetFragmentDirections.actionNavPlanetToNavPlanetDetail(item)
}