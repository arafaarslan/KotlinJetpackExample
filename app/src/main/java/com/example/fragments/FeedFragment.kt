package com.example.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.adapter.CountryAdapter
import com.example.countries.R
import com.example.viewmodel.CountryViewModel
import com.example.viewmodel.FeedViewModel

/**
 * Created by aarslan on 14/02/2022.
 */
class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        val countryListRecyclerView =
            view.findViewById(R.id.countryListRecyclerView) as RecyclerView
        countryListRecyclerView.layoutManager = LinearLayoutManager(context)
        countryListRecyclerView.adapter = countryAdapter

        val countryErrorTV = view.findViewById(R.id.countryErrorTV) as TextView
        val swipeRefreshLayout =
            view.findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        val countryLoadingPB = view.findViewById(R.id.countryLoadingPB) as ProgressBar

        swipeRefreshLayout.setOnRefreshListener {
            countryListRecyclerView.visibility = View.GONE
            countryErrorTV.visibility = View.GONE
            countryLoadingPB.visibility = View.VISIBLE

            viewModel.refreshDataFromAPI()

            swipeRefreshLayout.isRefreshing = false;
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        val countryListRecyclerView =
            view?.findViewById(R.id.countryListRecyclerView) as RecyclerView
        val countryErrorTV = view?.findViewById(R.id.countryErrorTV) as TextView
        val countryLoadingPB = view?.findViewById(R.id.countryLoadingPB) as ProgressBar

        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                countryListRecyclerView.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    countryErrorTV.visibility = View.VISIBLE
                    countryListRecyclerView.visibility = View.GONE
                } else {
                    countryErrorTV.visibility = View.INVISIBLE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    countryLoadingPB.visibility = View.VISIBLE
                    countryListRecyclerView.visibility = View.GONE
                    countryErrorTV.visibility = View.GONE

                } else {
                    countryLoadingPB.visibility = View.INVISIBLE
                }
            }
        })
    }
}

