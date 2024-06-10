package com.example.first_app.Data.models.garbage

import android.content.Context
import com.example.first_app.Data.models.NS_SEMK
import com.example.first_app.Data.models.Root

class DbRepository(context: Context): InterfaceDb {

    private val myDbManager = MyDbManager(context)

    override fun insertData(root: Root) {
        myDbManager.insertToDb(root)
    }

    override fun readData(columns: List<String>): ArrayList<NS_SEMK> {
        return myDbManager.readDbData(columns)
    }

    override fun deleteData() {
        myDbManager.deleteData()
    }
}