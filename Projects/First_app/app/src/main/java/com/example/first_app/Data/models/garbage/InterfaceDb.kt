package com.example.first_app.Data.models.garbage

import com.example.first_app.Data.models.NS_SEMK
import com.example.first_app.Data.models.Root

interface InterfaceDb {

    fun insertData(root: Root)

    fun readData(columns: List<String>): ArrayList<NS_SEMK>
    fun deleteData()

}