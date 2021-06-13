package com.example.appster


import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow
import kotlin.math.sqrt

private var x = 0F
private var y = 0F
private var z = 0F

private var acceleration = 0f
private var currentAcceleration = 0f
private var lastAcceleration = 0f

private lateinit var accelerometer: Sensor

private lateinit var listOfCategoriesCheckboxes : ArrayList<View>
private lateinit var listOfBlacklistCheckboxes : ArrayList<View>

private lateinit var queryString : String

class MainActivity : AppCompatActivity(), SensorEventListener{

    private lateinit var sensorManager : SensorManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            Log.d(ContentValues.TAG, "Success! we have an accelerometer")

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
            acceleration = 10f
            currentAcceleration = SensorManager.GRAVITY_EARTH
            lastAcceleration = SensorManager.GRAVITY_EARTH
        } else {
            // failed, we dont have an accelerometer!
            Log.e(ContentValues.TAG, "Failed. Unfortunately we do not have an accelerometer")
        }

        listOfCategoriesCheckboxes = getCheckboxChildren(linearLayoutCategories)
        listOfBlacklistCheckboxes  = getCheckboxChildren(linearLayoutBlacklist)

        anyCat_rb.isChecked = true
        greyOutCategoryCheckboxes(activity_main)

    }


    private fun getCheckboxChildren(v: View): ArrayList<View> {
        if (v !is ViewGroup) {
            val viewArrayList = ArrayList<View>()
            viewArrayList.add(v)
            return viewArrayList
        }
        val result = ArrayList<View>()
        val vg = v
        for (i in 0 until vg.childCount) {
            val child = vg.getChildAt(i)
            val viewArrayList = ArrayList<View>()
            if(v is CheckBox) {
                viewArrayList.add(v)
            }
            viewArrayList.addAll(getCheckboxChildren(child))
            result.addAll(viewArrayList)
        }
        return result
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event != null) {
            x = event.values[0]
            y = event.values[1]
            z = event.values[2]
        }
        val acceleration = sqrt(
            x.toDouble().pow(2.0) +
                    y.toDouble().pow(2.0) +
                    z.toDouble().pow(2.0)
        ) - SensorManager.GRAVITY_EARTH
        if (acceleration > 3) {
            val intent = Intent(this, TheJoke::class.java)
            val sb = StringBuilder()
            var anyFlag = false
            var anyCat = false
            queryString = "https://v2.jokeapi.dev/joke/"

            for(i in listOfCategoriesCheckboxes){
                if ((i as CheckBox).isChecked) {
                    anyCat = true
                    break
                }
            }

            if (anyCat_rb.isChecked){
                queryString += "Any"
            }
            else if (selectCat_rb.isChecked)
            {
                for(i in listOfCategoriesCheckboxes){
                    if ((i as CheckBox).isChecked) {
                        queryString = "$queryString${i.text},"
                    }
                }
                queryString = queryString.dropLast(1)
            }

            for(i in listOfBlacklistCheckboxes){
                if ((i as CheckBox).isChecked) {
                    anyFlag = true
                    break
                }
            }

            if (anyFlag) {
                queryString += "?blacklistFlags="
                for (i in listOfBlacklistCheckboxes) {
                    if ((i as CheckBox).isChecked) {
                        queryString = queryString + i.text.toString().lowercase(Locale.getDefault()) + ","
                    }
                }
                queryString = queryString.dropLast(1)
            }

            if(selectCat_rb.isChecked && !anyCat){
                Toast.makeText(this, "No category selected", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("x antes del intent", x.toString())
                intent.putExtra("x", x)
                intent.putExtra("y", y)
                intent.putExtra("z", z)
                intent.putExtra("querry", queryString)
                startActivity(intent)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }

    fun greyOutCategoryCheckboxes(view : View) {
        for(i in listOfCategoriesCheckboxes){
            (i as CheckBox).isEnabled = false
        }
    }

    fun unGreyCategoryCheckboxes(view : View) {
        for(i in listOfCategoriesCheckboxes){
            (i as CheckBox).isEnabled = true
        }
    }
}
