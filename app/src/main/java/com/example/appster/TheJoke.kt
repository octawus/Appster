package com.example.appster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class TheJoke : AppCompatActivity() {
        lateinit var textView: TextView
        lateinit var queue : RequestQueue
        lateinit var url : String

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_the_joke)

            val textView = findViewById<TextView>(R.id.joke_tv)

            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = "https://v2.jokeapi.dev/joke/Any?type=twopart"

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val topic = Gson().fromJson(response, Joke::class.java)
                    textView.text = topic.toString()
                },
                { textView.text = "That didn't work!" })

            // Add the request to the RequestQueue.
            queue.add(stringRequest)

        }

        fun get_da_joke(view : View){
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    // Display the first 500 characters of the response string.
                    val topic = Gson().fromJson(response, Joke::class.java)
                    textView.text = topic.toString()
                    // textView.text = "Response is: ${response.substring(0, 500)}"
                },
                { textView.text = "That didn't work!" })

            // Add the request to the RequestQueue.
            queue.add(stringRequest)}
    }