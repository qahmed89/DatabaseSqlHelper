package com.ahmed.resourse.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val TABLE_NAME = "country"
        const val DATABASE_NAME = "country.db"
        const val DATABASE_VERSION = 1
        const val _ID = "id"
        const val COUNTRY_NAME = "countryName"
        const val COUNTRY_CODE = "countryCode"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable: String = "CREATE TABLE" + TABLE_NAME + "(" +
                _ID + "INTEGER PRIMARY KEY " +
                COUNTRY_NAME + "TEXT NOT NULL" +
                COUNTRY_CODE + "INTEGER NOT NULL" + ");"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addCountry(countryName: String, countryCode: Int) : Boolean{
        val db: SQLiteDatabase? = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COUNTRY_NAME, countryName)
        contentValues.put(COUNTRY_CODE, countryCode)
       val result :Long? = db?.insert(TABLE_NAME, null, contentValues)
        val badResult : Long = -1
        if(result== badResult){
          return  false
        }else{
            return true
        }
        db?.close()
    }

    fun getCountryById(id: Int): Country? {
        var country: Country? = null
        val db: SQLiteDatabase = this.writableDatabase
        val query: String = "SELECT" + COUNTRY_NAME + "FROM" + TABLE_NAME + "WHERE" + _ID + "=" + id
        val cursor: Cursor = db.rawQuery(query, null)
        do {
            val countername = cursor.getString(cursor.getColumnIndexOrThrow(COUNTRY_NAME))
            country = Country(
                countryName = cursor.getString(cursor.getColumnIndexOrThrow(COUNTRY_NAME)),
                countryCode = cursor.getInt(2)
            )

        } while (cursor.moveToNext())

        return country
    }

    fun getAllCountries(): List<Country>? {
        var country: Country? = null
        var listCountries: ArrayList<Country>? = null
        val db: SQLiteDatabase = this.writableDatabase
        val query: String = "SELECT" + COUNTRY_NAME + "FROM" + TABLE_NAME + ";"
        val date: Cursor = db.rawQuery(query, null)
        do {
            country = Country(countryName = date.getString(1), countryCode = date.getInt(2))
            listCountries?.add(country)
        } while (date.moveToNext())

        return listCountries

    }
    fun updateItemById (id : Int , countryName: String , countryCode: Int){
        val db : SQLiteDatabase = this.writableDatabase
        val contentValues =ContentValues()
        contentValues.put(COUNTRY_NAME , countryName)
        contentValues.put(COUNTRY_CODE,countryCode)
        val whereQuery = _ID + "= ?"

        db.update(TABLE_NAME  , contentValues , whereQuery , arrayOf<String>(id.toString()) )

    }


}