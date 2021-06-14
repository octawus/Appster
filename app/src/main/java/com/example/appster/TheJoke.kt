package com.example.appster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_the_joke.*


class TheJoke : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_the_joke)


            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = intent.getStringExtra("querry").toString()

            val ref = FirebaseDatabase.getInstance().getReference("values")

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val topic = Gson().fromJson(response, Joke::class.java)
                    if (topic.joke == null) {
                        var text = "Setup: \n" + topic.setup + "\n"
                        text = text + "Delivery: \n" + topic.delivery
                        joke_tv.text = text
                    } else {
                        val text = "Joke: \n" + topic.joke
                        joke_tv.text = text
                    }
                },
                { joke_tv.text = "That didn't work!" })

            val x = intent.getFloatExtra("x", 0.0F).toString()
            val y = intent.getFloatExtra("y", 0.0F).toString()
            val z = intent.getFloatExtra("z", 0.0F).toString()

            val valuesId = ref.push().key
            val accelVals = valuesId?.let { AccelerometerValues(it,x, y, z) }
            if (valuesId != null) {
                ref.child(valuesId).setValue(accelVals)
            }
            if (valuesId != null) {
                ref.child(valuesId).setValue(accelVals)
            }

            // Add the request to the RequestQueue.
            queue.add(stringRequest)

        }

    }