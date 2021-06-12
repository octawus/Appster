package com.example.appster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_the_joke.*

class TheJoke : AppCompatActivity() {

        lateinit var queue : RequestQueue
        lateinit var url : String

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_the_joke)


            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = intent.getStringExtra("querry")

            Log.d("Aiudenmen", url.toString())
            // Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val topic = Gson().fromJson(response, Joke::class.java)
                    joke_tv.text = topic.toString()
                },
                { joke_tv.text = "That didn't work!" })

            // Add the request to the RequestQueue.
            queue.add(stringRequest)

        }
    }