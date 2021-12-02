package com.ahmed.resourse

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ahmed.resourse.db.SqlDatabaseHelper

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         val db = SqlDatabaseHelper(this)
       val result : Boolean =  db.addCountry("ALex" , 25)
        Log.i("MainActivity" , result.toString())
    }
}