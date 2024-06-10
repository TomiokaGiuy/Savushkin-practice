package com.example.first_app.Data.models.garbage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.first_app.Data.models.garbage.MyNameSQLite

class MyDbHelper(context: Context): SQLiteOpenHelper(context, MyNameSQLite.DATABASE_NAME,
    null, MyNameSQLite.DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(MyNameSQLite.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL(MyNameSQLite.SQL_DELETE_TABLE)
        onCreate(db)
    }


}