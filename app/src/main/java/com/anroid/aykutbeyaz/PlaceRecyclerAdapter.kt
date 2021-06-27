package com.anroid.aykutbeyaz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anroid.aykutbeyaz.entity.Place
import kotlinx.android.synthetic.main.activity_place_list.view.*
import kotlinx.android.synthetic.main.recycler_row.view.*
import kotlinx.android.synthetic.main.recyclerplace_row.view.*

class PlaceRecyclerAdapter(private val PlacesList: ArrayList<Place>)  : RecyclerView.Adapter<PlaceRecyclerAdapter.PlacesVH>() {

    class PlacesVH(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerplace_row,parent,false)
        return PlaceRecyclerAdapter.PlacesVH(itemView)
    }

    override fun onBindViewHolder(holder: PlacesVH, position: Int) {
        holder.itemView.recyclerPlaceView.text = PlacesList.get(position).getname()
    }

    override fun getItemCount(): Int {
       return PlacesList.size
    }

}