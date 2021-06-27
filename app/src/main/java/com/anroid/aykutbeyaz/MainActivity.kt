package com.anroid.aykutbeyaz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anroid.aykutbeyaz.entity.Country
import com.anroid.aykutbeyaz.entity.Place
import kotlinx.android.synthetic.main.activity_country_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_place_list.*
import org.json.JSONArray
import org.json.JSONException


class MainActivity : AppCompatActivity() {
    private val countryCreateUrl : String = "http://localhost:8080/createCountry?name=";
    private val countryListUrl : String = "http://localhost:8080/countrys";
    private val placeGetByIdListUrl : String = "http://localhost:8080/getListPlaces/";
    private val countryListArrayList = ArrayList<Country>()
    private val placeListArrayList = ArrayList<Place>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCountryList()

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val countryId : Int = countryListArrayList.get(position).getid()
                if(countryId != 0){
                    getPlaceList(countryId)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    fun goCountrysActivity(view: View){
        var name = country_Value.text.toString()
        val intent = Intent(applicationContext,CountryList::class.java)
        intent.putExtra("data",name)
        startActivity(intent)
    }

    fun goPlacesActivity(view: View){
        val intent = Intent(applicationContext,PlaceList::class.java)
        startActivity(intent)
    }

    fun createCountry(view : View){
        val name = country_Value.text.toString()
        if(name.length > 3){
            val statusMessage = AlertDialog.Builder(this)
            statusMessage.setTitle("Uyarı")
            statusMessage.setMessage("Ülkeyi eklemek istiyor musunuz ?")
            statusMessage.setPositiveButton("Evet"){ _, _ ->
              serviceCreateCountry(name)
                country_Value.text = null
            }
            statusMessage.setNegativeButton("Hayır"){ _, _ ->
                Toast.makeText(applicationContext, "Ülke eklenmedi.", Toast.LENGTH_SHORT).show()
                country_Value.text = null
            }
            statusMessage.show()
        }
        else{
            Toast.makeText(applicationContext,"En az 4 karakter girmelisiniz.",Toast.LENGTH_LONG).show()
        }
        closeKeyboard(view);
    }

    fun closeKeyboard(view: View){
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun serviceCreateCountry(name: String){
        try {
            val createServiceURL = "$countryCreateUrl$name"
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                Request.Method.GET, createServiceURL,
                { response ->  Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show() },
                { Toast.makeText(applicationContext, "Bir Hata Oluştu.", Toast.LENGTH_SHORT).show() })
            queue.add(stringRequest)
        }
        catch (e: Exception){
            Toast.makeText(applicationContext, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun getCountryList(){
        val queue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, countryListUrl, null,
            { response ->

                val emptyCountry : Country = Country()
                emptyCountry.id = 0
                emptyCountry.name = "Ülke Seçiniz..."
                countryListArrayList.add(emptyCountry)

                for (i in 0 until response.length()) {
                    var ulke : Country = Country()
                    try {
                        val responseObj = response.getJSONObject(i)
                        ulke.id = responseObj.getString("id").toInt()
                        ulke.name = responseObj.getString("name")
                        countryListArrayList.add(ulke)

                        val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, countryListArrayList )
                        spinner2.adapter = adapter

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
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

    fun getPlaceList(id : Int) {
        val getPlaceServiceUrl = "$placeGetByIdListUrl$id"
        val queueTwo = Volley.newRequestQueue(this@MainActivity)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, getPlaceServiceUrl, null,
            { response ->
                placeListArrayList.clear()

                val emptyPlace : Place = Place()
                emptyPlace.id = 0
                emptyPlace.name = "Şehir Seçiniz..."
                emptyPlace.countryid = 0
                placeListArrayList.add(emptyPlace)

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
            },
            {
                println("Hata oluştu.")
            })
        queueTwo.add<JSONArray>(jsonArrayRequest)
        placeSpinnerPush()
    }

    fun placeSpinnerPush(){
        val adapterTwo = ArrayAdapter( this, android.R.layout.simple_spinner_item, placeListArrayList )
        adapterTwo.notifyDataSetChanged()
        spinner.adapter = adapterTwo
    }
}