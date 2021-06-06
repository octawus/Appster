package com.example.appster


import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        var intent = Intent(this, TheJoke::class.java)

        val listOfCategoriesCheckboxes : ArrayList<View>? = getAllCheckboxChildren(linearLayoutCategories)
        val listOfBlacklistCheckboxes : ArrayList<View>? = getAllCheckboxChildren(linearLayoutBlacklist)



        if (listOfCategoriesCheckboxes != null) {
            for(i in listOfCategoriesCheckboxes){
                Log.v("Aiuditah", (i as CheckBox).text.toString())
            }
        }
    }

    private fun getAllCheckboxChildren(v: View): ArrayList<View>? {
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
            viewArrayList.addAll(getAllCheckboxChildren(child)!!)
            result.addAll(viewArrayList)
        }
        return result
    }
}
