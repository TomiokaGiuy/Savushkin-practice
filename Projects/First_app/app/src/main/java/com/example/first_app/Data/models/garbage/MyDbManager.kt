package com.example.first_app.Data.models.garbage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.first_app.Data.models.NS_SEMK
import com.example.first_app.Data.models.Root
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyDbManager(val context: Context) {

    val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null
    private var openCounter = 0


    fun insertToDb(root: Root) {

        openDB()

        CoroutineScope(Dispatchers.IO).launch {
            val jobs = root.NS_SEMK.map { ns_semk ->
                launch {
                    val values = ContentValues().apply {
                        put(MyNameSQLite.COLUMN_KMC, ns_semk.KMC)
                        put(MyNameSQLite.COLUMN_KRK, ns_semk.KRK)
                        put(MyNameSQLite.COLUMN_KT, ns_semk.KT)
                        put(MyNameSQLite.COLUMN_EMK, ns_semk.EMK)
                        put(MyNameSQLite.COLUMN_PR, ns_semk.PR)
                        put(MyNameSQLite.COLUMN_KTARA, ns_semk.KTARA)
                        put(MyNameSQLite.COLUMN_GTIN, ns_semk.GTIN)
                        put(MyNameSQLite.COLUMN_EMKPOD, ns_semk.EMKPOD)
                    }
                    Log.d("XMLparser", " time parsing $ns_semk")
                    db?.beginTransaction()
                    try {
                        db?.insert(MyNameSQLite.TABLE_NAME, null, values)
                        db?.setTransactionSuccessful()
                    } finally {
                        db?.endTransaction()
                    }
                }
            }
            jobs.forEach { it.join() }
            closeDb()
        }


    }

    fun readDbData(columns: List<String>) : ArrayList<NS_SEMK> {
        this.openDB()
        val dataList = ArrayList<NS_SEMK>()

        val columnsArray = columns.toTypedArray()
        val cursor = db?.query(MyNameSQLite.TABLE_NAME, columnsArray, null, null, null, null, null)

        var index = 0
        while (cursor?.moveToNext()!! && index < 10) {
            index++
            val nsSemk = NS_SEMK(
                KMC = cursor.getColumnIndex(MyNameSQLite.COLUMN_KMC).let { if (it != -1) cursor.getString(it) else null },
                KRK = cursor.getColumnIndex(MyNameSQLite.COLUMN_KRK).let { if (it != -1) cursor.getString(it) else null },
                KT = cursor.getColumnIndex(MyNameSQLite.COLUMN_KT).let { if (it != -1) cursor.getString(it) else null },
                EMK = cursor.getColumnIndex(MyNameSQLite.COLUMN_EMK).let { if (it != -1) cursor.getString(it) else "" },
                PR = cursor.getColumnIndex(MyNameSQLite.COLUMN_PR).let { if (it != -1) cursor.getString(it) else null },
                KTARA = cursor.getColumnIndex(MyNameSQLite.COLUMN_KTARA).let { if (it != -1) cursor.getString(it) else "" },
                GTIN = cursor.getColumnIndex(MyNameSQLite.COLUMN_GTIN).let { if (it != -1) cursor.getString(it) else null },
                EMKPOD = cursor.getColumnIndex(MyNameSQLite.COLUMN_EMKPOD).let { if (it != -1) cursor.getString(it) else null }
            )
            dataList.add(nsSemk)
        }

        cursor.close()
        this.closeDb()
        return dataList
    }


    fun deleteData() {
        openDB()
        CoroutineScope(Dispatchers.IO).launch {
            db?.beginTransaction()
            try {
                db?.execSQL("DELETE FROM ${MyNameSQLite.TABLE_NAME}")
                db?.setTransactionSuccessful()
            } catch (e: Exception) {
                Log.e("DatabaseError", "Error while trying to delete all data", e)
            } finally {
                db?.endTransaction()
                closeDb()
            }
        }
    }


    fun openDB(){

        db = myDbHelper.writableDatabase
        openCounter++
    }
    fun closeDb(){
        openCounter--
        if (openCounter==0){
            myDbHelper.close()
        }

    }
}
