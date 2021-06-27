package com.anroid.aykutbeyaz

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.anroid.aykutbeyaz.entity.Country
import kotlinx.android.synthetic.main.activity_country_list.*
import org.json.JSONArray
import org.json.JSONException
import kotlin.collections.ArrayList


class CountryList : AppCompatActivity() {
    private val countryListUrl : String = "http://localhost:8080/countrys";
    private val countryRemoveUrl : String = "http://localhost:8080/removeCountry/";
    private val countryListArrayList = ArrayList<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)

        val queue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, countryListUrl, null,
            { response ->
                for (i in 0 until response.length()) {
                    var ulke : Country = Country()
                    try {
                        val responseObj = response.getJSONObject(i)
                        ulke.id = responseObj.getString("id").toInt()
                        ulke.name = responseObj.getString("name")
                        countryListArrayList.add(ulke)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                val layoutManager = LinearLayoutManager(this)
                recyclerView.layoutManager = layoutManager
                val adapter = CountryRecyclerAdapter(countryListArrayList)
                recyclerView.adapter = adapter
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