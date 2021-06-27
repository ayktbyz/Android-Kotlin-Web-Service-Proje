package com.anroid.aykutbeyaz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anroid.aykutbeyaz.entity.Country
import kotlinx.android.synthetic.main.recycler_row.view.*

class CountryRecyclerAdapter(private val CountryList: ArrayList<Country>) : RecyclerView.Adapter<CountryRecyclerAdapter.CountrysVH>() {

    class CountrysVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountrysVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return CountrysVH(itemView)
    }

    override fun onBindViewHolder(holder: CountrysVH, position: Int) {
        holder.itemView.recyclerCountryView.text = CountryList.get(position).getname()

        holder.itemView.recyclerDeleteBtn.setOnClickListener {
            val btnid = CountryList.get(position).getid()
            removeAt(CountryList.get(position),position)
            notifyDataSetChanged()
            println("butona basildi. $btnid")
        }
    }

    override fun getItemCount(): Int {
       return CountryList.size
    }

    fun removeAt(country: Country,position: Int) {
        CountryList.remove(country)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, CountryList.size)
    }

}