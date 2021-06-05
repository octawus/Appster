package com.example.appster

import android.content.ContentValues.TAG
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlin.math.abs
import kotlin.math.sqrt


class Accelerometer// failed, we dont have an accelerometer!
    (sm: SensorManager) : SensorEventListener {

    private val x = 0F
    private val y = 0F
    private val z = 0F

    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    private lateinit var accelerometer: Sensor
    private var sensorManager: SensorManager = sm


    init {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            Log.d(TAG, "Success! we have an accelerometer")

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            // failed, we dont have an accelerometer!
            Log.e(TAG, "Failed. Unfortunately we do not have an accelerometer")
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
        }
        lastAcceleration = currentAcceleration
        currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
        val delta: Float = currentAcceleration - lastAcceleration
        acceleration = acceleration * 0.9f + delta
        if (acceleration > 1) {
            Log.d("Sarinhita", "Me he mobido iueputa")
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}