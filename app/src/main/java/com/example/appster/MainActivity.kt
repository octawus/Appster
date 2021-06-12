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
            queryString = "https://v2.jokeapi.dev/joke/"

            if (anyCat_rb.isActivated){
                queryString += "Any"
            }
            else if (selectCat_rb.isActivated)
            {
                for(i in listOfCategoriesCheckboxes){
                    if ((i as CheckBox).isChecked) {
                        queryString = "$queryString$i,"
                    }
                }
            }

            for(i in listOfBlacklistCheckboxes){
                if ((i as CheckBox).isChecked) {
                    anyFlag = true
                    break
                }
            }

            if (anyFlag) {
                sb.append(queryString).append("?blacklistFlags=")
                for (i in listOfBlacklistCheckboxes) {
                    if ((i as CheckBox).isChecked) {
                        queryString = queryString + i.toString().lowercase(Locale.getDefault()) + ","
                    }
                }
            }

            intent.putExtra("querry", queryString)
            startActivity(intent)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }

    fun greyOutCategoryCheckboxes() {
        for(i in listOfCategoriesCheckboxes){
            (i as CheckBox).isEnabled = false
        }
    }

    fun unGreyCategoryCheckboxes() {
        for(i in listOfCategoriesCheckboxes){
            (i as CheckBox).isEnabled = true
        }
    }
}
