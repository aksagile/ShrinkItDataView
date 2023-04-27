package com.example.shrinkitdataview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import android.widget.Button

import java.io.BufferedReader
import java.net.URL
import java.net.HttpURLConnection
import kotlin.io.bufferedReader as bufferedReader1
import android.util.Log
import android.widget.RelativeLayout
import com.android.volley.toolbox.StringRequest
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    lateinit var userRVAdapter: RvAdapter
    lateinit var layoutManager: LinearLayoutManager
    //lateinit var readProgressLayout: RelativeLayout
    lateinit var userRV: RecyclerView
    //var loadingPB: ProgressBar? = nullg binding
    lateinit var publishButton:Button
    lateinit var userArrayList : ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setContentView(R.layout.user_item)
        //userModalArrayList = ArrayList()
        //userRV = findViewById(R.id.rvUserNames)
        //loadingPB = findViewById(R.id.idPBLoading)
        //val binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.get)
        userRV=findViewById(R.id.rvUserNames)
        layoutManager= LinearLayoutManager(this)
        userArrayList = ArrayList()

        publishButton = findViewById(R.id.publishStepsButton)
        publishButton.setOnClickListener {
            // Send POST request here
            val steps = "67890"
            val name = "ankesh"
            val teamName = "Strata"
            sendRequest(name, teamName, steps);
        }
        getDataFromAPI2()
    }

    private fun sendRequest(name : String, teamName: String, steps: String) {
        val url="https://script.google.com/macros/s/AKfycbyGVJB0oSn8UogxjHIQAsO-ldPmqBhiVdoDhKzf4GVQlEZS0F0oS_bqKehoeFdd7fGTtw/exec?action=create&id=17&name="+name+"&teamName="+teamName+"&steps="+steps
        val stringRequest =  StringRequest(Request.Method.GET, url,
            Response.Listener {
                Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
                //writeProgressLayout.visibility = View.GONE
                //writeProgressBar.visibility = View.GONE
            },
            Response.ErrorListener {
                Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
                //writeProgressLayout.visibility = View.GONE
                //writeProgressBar.visibility = View.GONE
                Log.d("API", "that didn't work")
            })
        val queue = Volley.newRequestQueue(this@MainActivity)
        queue.add(stringRequest)
    }

    private fun getDataFromAPI2(){
        val queue = Volley.newRequestQueue(this@MainActivity)
        //val userList= arrayListOf<User>()
        val url=
            "https://sheets.googleapis.com/v4/spreadsheets/1AxdOnbYmcTYK33S9UTPtAf3farEbsZCVrnhgnjfd9NE/values/stepsData?alt=json&key=AIzaSyDzQoARLFZ1KCld4REAOoYZWZ2N1nZHRf0"
        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            val jsonArray = response.getJSONArray("values")
            for (i in 1 until 5) {
                val user = jsonArray.getJSONArray(i)
                val name = user[2].toString()
                val teamName = user[3].toString()
                val steps = user[4].toString()
                userArrayList.add(User(name, teamName, steps))
            }
            userRVAdapter = RvAdapter(userArrayList)
            userRV.layoutManager = LinearLayoutManager(this@MainActivity)
            userRV.adapter = userRVAdapter
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener {
            Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
            Log.d("API", "that didn't work")
        })
        queue.add(request)
    }


    /*private fun getDataFromAPI() {
        val url=
            "https://sheets.googleapis.com/v4/spreadsheets/1AxdOnbYmcTYK33S9UTPtAf3farEbsZCVrnhgnjfd9NE/values/stepsData?alt=json&key=AIzaSyDzQoARLFZ1KCld4REAOoYZWZ2N1nZHRf0"
            //"https://sheets.googleapis.com/v4/spreadsheets/YOUR_SHEET_ID/values/YOUR_SHEET_NAME?alt=json&key=YOUR_API_KEY"
        val queue = Volley.newRequestQueue(this@MainActivity)

        val jsonObjectRequest =
            JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {
                        //loadingPB!!.visibility = View.GONE
                        try {
                            // val feedObj = response.getJSONObject("")
                            val entryArray = response.getJSONArray("values")

                            for (i in 1 until 10) {
                                val entryObj = entryArray.getJSONArray(i)
                                val name = entryObj[2].toString()
                                val teamName = entryObj[3].toString()
                                //  entryObj.getJSONObject("gsx\$lastname").getString("\$t")
                                val steps = entryObj[4].toString()

                                userModalArrayList!!.add(Model(name, teamName, steps))


                                // passing array list to our adapter class.
                                userRVAdapter = rvAdapter(userModalArrayList!!)

                                // setting layout manager to our recycler view.
                                userRV!!.layoutManager = LinearLayoutManager(this@MainActivity)

                                // setting adapter to our recycler view.
                                userRV!!.adapter = userRVAdapter
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        // handline on error listener method.
                        Toast.makeText(this@MainActivity, "Fail to get data..", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        queue.add(jsonObjectRequest)
    }*/

}