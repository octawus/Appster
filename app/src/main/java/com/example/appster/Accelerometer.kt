package com.example.appster

import android.content.ContentValues.TAG
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlin.math.abs


class Accelerometer// failed, we dont have an accelerometer!
    (sm: SensorManager) : SensorEventListener {

    private var lastX = 0F
    private var lastY = 0F
    private var lastZ = 0F

    private var deltaX = 0f
    private var deltaY = 0f
    private var deltaZ = 0f
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

    override fun onSensorChanged(p0: SensorEvent?) {

        // get the change of the x,y,z values of the accelerometer
        if (p0 != null) {
            deltaX = abs(lastX - p0.values[0])
            deltaY = abs(lastY - p0.values[1])
            deltaZ = abs(lastZ - p0.values[2])
        }

        // if the change is below 2, it is just plain noise. Discard it!
        if (deltaX < 2)
            deltaX = 0F
        if (deltaY < 2)
            deltaY = 0F
        if (deltaZ < 2)
            deltaZ = 0F

        // set the last know values of x,y,z
        if (p0 != null) {
            lastX = p0.values[0]
            lastY = p0.values[1]
            lastZ = p0.values[2]
        }

        //Log.d("Sarinhita", "Me he mobido iueputa")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}