package com.anroid.aykutbeyaz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.anroid.aykutbeyaz.entity.Country
import com.anroid.aykutbeyaz.entity.Place
import kotlinx.android.synthetic.main.activity_country_list.*
import kotlinx.android.synthetic.main.activity_country_list.recyclerView
import kotlinx.android.synthetic.main.activity_place_list.*
import org.json.JSONArray
import org.json.JSONException

class PlaceList : AppCompatActivity() {
    private val placeListUrl : String = "http://localhost:8080/places";
    private val placeListArrayList = ArrayList<Place>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)

        val queue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, placeListUrl, null,
            { response ->
                for (i in 0 until response.length()) {
                    var sehir : Place = Place()
                    try {
                        val responseObj = response.getJSONObject(i)
                        sehir.id = responseObj.getString("id").toInt()
                        sehir.name = responseObj.getString("name")
                        sehir.countryid = responseObj.getString("countryid").toInt()
                        placeListArrayList.add(sehir)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                val layoutManager = LinearLayoutManager(this)
                recyclerViewPlace.layoutManager = layoutManager
                val adapter = PlaceRecyclerAdapter(placeListArrayList)
                recyclerViewPlace.adapter = adapter
            },
            {
                Toast.makeText(
                    this,
                    "Hata oluştu.",
                    Toast.LENGTH_SHORT
                ).show()
            })
        queue.add<JSONArray>(jsonArrayRequest)
    }
}