package com.example.appster

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlin.math.pow
import kotlin.math.sqrt


class Accelerometer// failed, we dont have an accelerometer!
    (sm: SensorManager, cont: Context) : SensorEventListener {

    private var x = 0F
    private var y = 0F
    private var z = 0F

    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    private lateinit var accelerometer: Sensor
    private var sensorManager: SensorManager = sm
    private val context : Context = cont

    init {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            Log.d(TAG, "Success! we have an accelerometer")

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
            acceleration = 10f
            currentAcceleration = SensorManager.GRAVITY_EARTH
            lastAcceleration = SensorManager.GRAVITY_EARTH
        } else {
            // failed, we dont have an accelerometer!
            Log.e(TAG, "Failed. Unfortunately we do not have an accelerometer")
        }
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
            Log.d("Sarinhita", "Me he mobido iueputa")
            val i = Intent(context, TheJoke::class.java)
            context.startActivity(i)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}