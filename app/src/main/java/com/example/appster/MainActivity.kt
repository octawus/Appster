package com.example.appster


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){


    lateinit var textView: TextView
    lateinit var queue : RequestQueue
    lateinit var url : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
