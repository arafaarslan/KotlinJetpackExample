package com.example.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.countries.R
import com.example.countries.databinding.FragmentCountryBinding
import com.example.util.downloadFromUrl
import com.example.util.placeHolderProgressBar
import com.example.viewmodel.CountryViewModel

/**
 * Created by aarslan on 14/02/2022.
 */
class CountryFragment : Fragment() {

    private lateinit var viewModel: CountryViewModel
    private var countryUuid = 0;
    private lateinit var dataBinding: FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_country, container, false)
        //return inflater.inflate(R.layout.fragment_country, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)

        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {

                dataBinding.selectedCountry = country
              // val countryFlagIV = view?.findViewById(R.id.countryFlagIV) as ImageView
              // val countryNameTV = view?.findViewById(R.id.countryNameTV) as TextView
              // val countryCapitalTV = view?.findViewById(R.id.countryCapitalTV) as TextView
              // val countryRegionTV = view?.findViewById(R.id.countryRegionTV) as TextView
              // val countryLanguageTV = view?.findViewById(R.id.countryLanguageTV) as TextView
              // val countryCurrencyTV = view?.findViewById(R.id.countryCurrencyTV) as TextView

              // context?.let {
              //     countryFlagIV.downloadFromUrl(country.flagUrl, placeHolderProgressBar(it))
              // }
              // countryNameTV.setText(country.name)
              // countryCapitalTV.setText(country.capital)
              // countryRegionTV.setText(country.region)
              // countryLanguageTV.setText(country.language)
              // countryCurrencyTV.setText(country.currency)
            }
        })
    }
}