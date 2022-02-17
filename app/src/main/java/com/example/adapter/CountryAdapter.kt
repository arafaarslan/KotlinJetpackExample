package com.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.databinding.ItemCountryBinding
import com.example.fragments.FeedFragmentDirections
import com.example.model.Country
import com.example.util.downloadFromUrl
import com.example.util.placeHolderProgressBar
import org.w3c.dom.Text
import java.util.*

/**
 * Created by aarslan on 16/02/2022.
 */
class CountryAdapter(val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener {

    class CountryViewHolder(var view : ItemCountryBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
     //   val view = inflater.inflate(R.layout.item_country,parent,false)
        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
/*
*
*        val countryNameTV  = holder.view.root.findViewById(R.id.countryNameTV) as TextView
        val countryRegionTV  = holder.view.root.findViewById(R.id.countryRegionTV) as TextView
        val countryFlagIV  = holder.view.root.findViewById(R.id.countryFlagIV) as ImageView

       // holder.view.countryNameTV.setText("")
        countryNameTV.setText(countryList.get(position).name)
        countryRegionTV.setText(countryList.get(position).region)

        holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }

        countryFlagIV.downloadFromUrl(countryList.get(position).flagUrl, placeHolderProgressBar(holder.view.context))

 * */

        holder.view.country = countryList.get(position)
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return countryList.size;
    }

    //for SwipeRefreshLayout
    fun updateCountryList(newCountryList : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()//Update adapter
    }

    override fun onCountryClicked(v: View) {
        val uuidTV = v.findViewById(R.id.uuidCountryText) as TextView
        val uuidVal = uuidTV.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuidVal)
        Navigation.findNavController(v).navigate(action)

    }

}


